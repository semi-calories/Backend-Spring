package com.example.demo.dto.User.Request;

import com.example.demo.domain.User.Gender;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 정보 수정",
        notes = "유저의 정보를 수정한다.")
public class RequestUserUpdateDto {

    private Long userCode;
    private String email;
    private MultipartFile image;
    private String name;
    private Gender gender;
    private int age;
    private double height;
    private double weight;
    private int period=1;

    private String userActivity;
    private double goalWeight;
    private String userGoal;
}
