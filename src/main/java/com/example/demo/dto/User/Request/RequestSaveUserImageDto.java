package com.example.demo.dto.User.Request;

import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 이미지 저장",
        notes = "유저의 이미지를 저장한다.")
public class RequestSaveUserImageDto {
    private Long userCode;
    private MultipartFile image;
}
