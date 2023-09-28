package com.example.demo.dto.User.Response;

import com.example.demo.domain.User.Diet.DietRecord;
import com.example.demo.domain.User.Diet.UserSatisfaction;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 식단 기록 응답",
        notes = "유저의 식단 기록 조회 요청에 응답한다.")
@ToString
public class UserRecordDto {

    private DietRecord dietRecordList;
    private UserSatisfaction userSatisfactionList;
    private Long foodCode;


}
