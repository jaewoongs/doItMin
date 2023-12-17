package com.doitmin.webapp.api.dto;

import lombok.Data;

@Data
public class BoardPostDto {
    private String title;
    private String content;
    private String latitude;
    private String longitude;
    private String address;
    private String addressDetail;
    private String addressExtraInfo;
    private String boardImageUrl;
}
