package com.doitmin.webapp.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    @Column(unique = true)
    private String refreshToken;
    private Date expiredAt;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
