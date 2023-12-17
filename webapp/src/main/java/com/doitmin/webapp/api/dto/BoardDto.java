package com.doitmin.webapp.api.dto;

import com.doitmin.webapp.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private WriteUserDto writer;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private boolean isDeleted;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String address;
    private String addressDetail;
    private String addressExtraInfo;

    @Data
    public static class WriteUserDto {
        private Long id;
        private String nickname;
        private String profileImageUrl;
    }
}
