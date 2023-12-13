package com.doitmin.webapp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {
    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "1234")
    private String password;
}
