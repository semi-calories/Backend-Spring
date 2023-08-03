package com.example.demo.dto.User.Request;

import com.example.demo.domain.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 기본 정보 수정",
        notes = "유저의 기본 정보를 수정한다.")
public class RequestUserInfoUpdateDto {

    @JsonProperty("user-code")
    private Long userCode;
    private String email;
    private String phone;
    private String image;
    private String name;
    private Gender gender;
    private int age;
    private double height;
    private double weight;
}
