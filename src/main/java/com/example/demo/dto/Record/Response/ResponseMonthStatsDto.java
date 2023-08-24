package com.example.demo.dto.Record.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseMonthStatsDto {

    private List<List<Double>> monthList = new ArrayList<>();
}
