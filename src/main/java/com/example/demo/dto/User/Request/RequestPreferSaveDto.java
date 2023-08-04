package com.example.demo.dto.User.Request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 선호 음식 저장",
        notes = "유저의 선호 음식을 저장한다.")
public class RequestPreferSaveDto {

    @JsonProperty("user-code")
    private Long userCode;
    @JsonProperty("prefer-list")
    private List<Long> preferList = new ArrayList<>();
}
