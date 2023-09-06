package com.example.demo.dto.User.Response;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 식단 기록 응답",
        notes = "유저의 식단 기록 조회 요청에 응답한다.")
@ToString
public class ResponseUserRecordDto {

    private List<UserRecordDto> userRecordDtos = new ArrayList<>();

}
