package com.example.demo.dto.Recognizer.Logmeal.ResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Segmentation_results {
    @JsonProperty("recognition_results")
    private List<Recognition_results> recognition_results;
}
