package com.example.demo.dto.Record.Response;

import com.example.demo.domain.User.PredictUserWeight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class ResponsePredictWeightDto {
    private List<PredictWeightDto> predictWeightList = new ArrayList<>();

    public ResponsePredictWeightDto(List<PredictUserWeight> predictWeight) {
        predictWeight.forEach(weightList ->{
            predictWeightList.add(new PredictWeightDto(weightList.getTimestamp(), weightList.getPredictWeight()));
        });
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    class PredictWeightDto{
        private LocalDateTime timestamp;
        private Double weight;
    }
}


