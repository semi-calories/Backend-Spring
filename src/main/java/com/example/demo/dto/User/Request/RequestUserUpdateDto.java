package com.example.demo.dto.User.Request;

import com.example.demo.domain.User.Gender;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 정보 수정",
        notes = "유저의 정보를 수정한다.")
public class RequestUserUpdateDto {

    private Long userCode;
    private String email;
    private String image;
    private String name;
    private Gender gender;
    private int age;
    private double height;
    private double weight;
    private int period;

    private String userActivity;
    private double goalWeight;
    private String userGoal;
}
