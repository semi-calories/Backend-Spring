package com.example.demo.dto.User.Response;

import com.example.demo.domain.Gender;
import com.example.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 기본 정보 조회 응답",
        notes = "유저의 기본 정보 조회 요청에 응답한다.")
public class ResponseUserInfoGetDto {

    private String email;
    private String name;
    private int age;
    private Gender gender;
    private String phone;
    private byte[] image;
    private double height;
    private double weight;

    public ResponseUserInfoGetDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.height = user.getHeight();
        this.weight = user.getWeight();
    }
}
