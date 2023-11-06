package com.example.demo.dto.Record.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeightDto{
    private LocalDateTime timestamp;
    private Double weight;
    private Double predictWeight;
}