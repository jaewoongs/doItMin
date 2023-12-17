package com.doitmin.webapp.api.entities;

import com.doitmin.webapp.api.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    public User user;
    public RoleName roleName;
    @Id
    @GeneratedValue
    long roleId;

    public Role(RoleName role) {
        this.roleName = role;
    }
}
