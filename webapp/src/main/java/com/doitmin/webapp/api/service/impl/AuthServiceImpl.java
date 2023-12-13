package com.doitmin.webapp.api.service.impl;

import com.doitmin.webapp.api.dto.AuthTokenDto;
import com.doitmin.webapp.api.entities.RefreshToken;
import com.doitmin.webapp.api.entities.User;
import com.doitmin.webapp.api.repository.RefreshTokenRepository;
import com.doitmin.webapp.api.repository.UserRepository;
import com.doitmin.webapp.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours in seconds
    private static final String SECRET_KEY = "doitmin"; // Define a strong secret key

    @Override
    public AuthTokenDto signUp(User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        User newUser = userRepository.save(user);

        // Generate and save the refresh token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(newUser);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiredAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days validity
        refreshTokenRepository.save(refreshToken);

        // Generate the access token
        String accessToken = generateAccessToken(newUser);

        return new AuthTokenDto(refreshToken.getRefreshToken(),accessToken);
    }

    private String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public AuthTokenDto signIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiredAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days validity
        refreshTokenRepository.save(refreshToken);

        // Generate the access token
        String accessToken = generateAccessToken(user);
        return new AuthTokenDto(refreshToken.getRefreshToken(),accessToken);
    }

    @Override
    public void signOut(User user) {

    }

    @Override
    public AuthTokenDto refresh(String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByRefreshTokenAndExpiredAtLessThanOrderByIdDesc(refreshToken, new Date(System.currentTimeMillis()));
        if(refreshToken1 == null){
            throw new RuntimeException("Invalid refresh token");
        }
        User user = refreshToken1.getUser();

        RefreshToken newToken = new RefreshToken();
        newToken.setUser(user);
        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setExpiredAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days validity
        refreshTokenRepository.save(newToken);

        // Generate the access token
        String accessToken = generateAccessToken(user);
        return new AuthTokenDto(newToken.getRefreshToken(),accessToken);
    }

    @Override
    public void revoke(User user) {
        refreshTokenRepository.deleteAll(user.getRefreshToken());
    }
}
