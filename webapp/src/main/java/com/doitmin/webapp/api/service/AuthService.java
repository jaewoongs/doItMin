package com.doitmin.webapp.api.service;

import com.doitmin.webapp.api.dto.AuthTokenDto;
import com.doitmin.webapp.api.entities.User;

public interface AuthService {

    public AuthTokenDto signUp(User user);
    public AuthTokenDto signIn(String email, String password);
    public void signOut(User user);
    public AuthTokenDto refresh(String refreshToken);
    public void revoke(User user);
}
