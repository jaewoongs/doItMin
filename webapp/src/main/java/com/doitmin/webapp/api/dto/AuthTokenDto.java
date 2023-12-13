package com.doitmin.webapp.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDto {
    private String refreshToken;
    private String accessToken;
}
