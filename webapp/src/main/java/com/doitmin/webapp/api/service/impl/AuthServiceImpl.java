package com.doitmin.webapp.api.service.impl;

import com.doitmin.webapp.api.dto.AuthTokenDto;
import com.doitmin.webapp.api.dto.ProfileDto;
import com.doitmin.webapp.api.entities.RefreshToken;
import com.doitmin.webapp.api.entities.User;
import com.doitmin.webapp.api.enums.RoleName;
import com.doitmin.webapp.api.repository.RefreshTokenRepository;
import com.doitmin.webapp.api.repository.UserRepository;
import com.doitmin.webapp.api.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

import static com.doitmin.webapp.configuration.JwtUtil.generateAccessToken;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AuthTokenDto signUp(User user) {
        User exist = userRepository.findByEmail(user.getEmail());
        if (exist != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 메일입니다.");
        }
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the user
        User newUser = userRepository.save(user);
        addRoleToUser(newUser.getId(), RoleName.USER);

        // Generate and save the refresh token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(newUser);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiredAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days validity
        refreshTokenRepository.save(refreshToken);

        // Generate the access token
        String accessToken = generateAccessToken(newUser);

        return new AuthTokenDto(refreshToken.getRefreshToken(), accessToken);
    }


    public User addRoleToUser(Long userId, RoleName roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.addRole(roleName);

        return userRepository.save(user);
    }

    @Override
    public AuthTokenDto signIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 없음");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 틀림");
        }
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiredAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days validity
        refreshTokenRepository.save(refreshToken);

        // Generate the access token
        String accessToken = generateAccessToken(user);
        return new AuthTokenDto(refreshToken.getRefreshToken(), accessToken);
    }

    @Override
    public ProfileDto getProfile(ProfileDto profileDto) {
        return profileDto;
    }

    @Override
    public void signOut(User user) {

    }

    @Override
    public AuthTokenDto refresh(String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByRefreshTokenAndExpiredAtGreaterThanOrderByIdDesc(refreshToken, new Date(System.currentTimeMillis()));
        if (refreshToken1 == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰 없음");
        }
        User user = refreshToken1.getUser();

        RefreshToken newToken = new RefreshToken();
        newToken.setUser(user);
        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setExpiredAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days validity
        refreshTokenRepository.save(newToken);

        // Generate the access token
        String accessToken = generateAccessToken(user);
        return new AuthTokenDto(newToken.getRefreshToken(), accessToken);
    }

    @Override
    public void revoke(User user) {
        refreshTokenRepository.deleteAll(user.getRefreshTokens());
    }
}
