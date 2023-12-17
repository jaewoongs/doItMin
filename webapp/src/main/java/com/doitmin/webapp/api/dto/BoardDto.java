package com.doitmin.webapp.api.dto;

import com.doitmin.webapp.api.entities.User;
import lombok.Data;

@Data
public class BoardDto {
    private Long id;
    private String title;
    private String content;
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