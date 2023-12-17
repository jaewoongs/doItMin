package com.doitmin.webapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
