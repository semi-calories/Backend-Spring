package com.example.demo.dto.Recognizer.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseFoodRecogDto {
    private List<String> names = new ArrayList<>();

}
