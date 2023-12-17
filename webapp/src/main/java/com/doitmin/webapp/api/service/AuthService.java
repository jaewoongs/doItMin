package com.doitmin.webapp.api.service;

import com.doitmin.webapp.api.dto.AuthTokenDto;
import com.doitmin.webapp.api.dto.ProfileDto;
import com.doitmin.webapp.api.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {

    AuthTokenDto signUp(User user, MultipartFile profileImage) throws IOException;

    AuthTokenDto signIn(String email, String password);

    void signOut(User user);

    AuthTokenDto refresh(String refreshToken);

    void revoke(User user);

    ProfileDto getProfile(ProfileDto profileDto);
}
