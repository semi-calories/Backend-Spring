package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@ApiOperation(
        value = "식단 기록 주간 통계 요청",
        notes = "사용자의 주간 식단 통계 조회를 요청한다.")
public class RequestWeekStatDto {

    private List<WeekDto> calculateWeek = new ArrayList<>();
}
