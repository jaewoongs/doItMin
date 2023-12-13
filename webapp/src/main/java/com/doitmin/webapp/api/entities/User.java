package com.doitmin.webapp.api.entities;

import com.doitmin.webapp.api.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    String email;
    String password;
    String salt;
    String nickname;
    String profileImageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();

    public void addRole(RoleName roleName) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        Role newRole = new Role(roleName);
        roles.add(newRole);
        newRole.setUser(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.setUser(null);
    }
}
