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
        value = "유저 선호도 음식 저장",
        notes = "유저의 선호도 음식을 저장한다.")
public class RequestPreferenceSaveDto {


    private Long userCode;

    private List<Long> foodList = new ArrayList<>();
}
