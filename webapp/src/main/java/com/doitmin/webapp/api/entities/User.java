package com.doitmin.webapp.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id @GeneratedValue
    Long id;
    String email;
    String password;
    String salt;
    String nickname;
    String profileImageUrl;
    @OneToMany
    List<RefreshToken> refreshToken;
}
