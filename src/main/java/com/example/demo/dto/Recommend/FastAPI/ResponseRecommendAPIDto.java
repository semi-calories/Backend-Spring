package com.example.demo.dto.Recommend.FastAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRecommendAPIDto {
    private List<Integer> result;
}
