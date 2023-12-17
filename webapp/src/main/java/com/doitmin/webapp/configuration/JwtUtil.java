package com.doitmin.webapp.configuration;

import com.doitmin.webapp.api.dto.ProfileDto;
import com.doitmin.webapp.api.entities.Role;
import com.doitmin.webapp.api.entities.User;
import com.doitmin.webapp.api.enums.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {


    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours in seconds
    private static final String SECRET_KEY = "doitmin12e3wqeafio23qh9qo23fhf2q39"; // Define a strong secret key

    public static String generateAccessToken(User user) {
        List<RoleName> roleNames = user.getRoles().stream()
                .map(Role::getRoleName) // Assuming Role has a getName() method
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email",user.getEmail())
                .claim("nickname", user.getNickname())
                .claim("roles", roleNames)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public static boolean validateAccessToken(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build().parseSignedClaims(jwt);
            return true;
        } catch (Exception e) {
//            System.out.println(e);
        }
        return false;
    }

    public static ProfileDto parseAccessToken(String jwt) {
        try {
            // Parse the JWT token
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build().parseSignedClaims(jwt);

            Claims claims = claimsJws.getPayload();

            // Extract the required information from claims
            Long id = Long.valueOf(claims.getSubject());
            String email = claims.get("email", String.class);
            String nickname = claims.get("nickname", String.class);
            List<String> roles = claims.get("roles", List.class);

            // Create and return a ProfileDto object
            ProfileDto profile = new ProfileDto();
            profile.setId(Long.valueOf(id));
            profile.setEmail(email);
            profile.setNickname(nickname);
            profile.setRoleNames(roles); // Ensure ProfileDto has a field to store roles

            return profile;
        } catch (Exception e) {
            // Handle the exception based on your application's requirements
            // For example, you could log the error and/or return null or a default ProfileDto object
            return null;
        }
    }
}