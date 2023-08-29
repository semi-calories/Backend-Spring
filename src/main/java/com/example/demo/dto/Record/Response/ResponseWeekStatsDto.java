package com.example.demo.dto.Record.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseWeekStatsDto {

    private List<List<Double>> weekList = new ArrayList<>();
}
