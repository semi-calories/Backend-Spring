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
        value = "유저 비선호 음식 저장",
        notes = "유저의 비선호 음식을 저장한다.")
public class RequestDislikeSaveDto {


    private Long userCode;

    private List<Long> dislikeList = new ArrayList<>();
}
