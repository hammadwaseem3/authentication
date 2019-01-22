package com.example.authentication.demo.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResponseDto {

    private String accessToken;
    private String refreshToken;

}
