package com.doitmin.webapp.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn
    private User writer;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private boolean isDeleted;
    private String imageUrl;
    private long latitude;
    private long longitude;
    private String address;
    private String addressDetail;
    private String addressExtraInfo;
}
