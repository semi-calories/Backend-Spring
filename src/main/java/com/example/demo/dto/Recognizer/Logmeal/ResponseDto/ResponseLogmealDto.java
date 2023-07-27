package com.example.demo.dto.Recognizer.Logmeal.ResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseLogmealDto {

    @JsonProperty("segmentation_results")
    private List<Segmentation_results> segmentation_results;

    public void setSegmentation_results(List<Segmentation_results> segmentation_results) {
        this.segmentation_results = segmentation_results;
    }
}
