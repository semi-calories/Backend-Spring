package com.example.demo.dto.Recommend.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.security.DenyAll;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecommendDto {

    @JsonProperty("user-code")
    private Long userCode;
    @JsonProperty("eat-times")
    private int eatTimes;
}
