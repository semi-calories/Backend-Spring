package com.example.demo.dto.Recognizer.Response;

import com.example.demo.dto.Recognizer.Logmeal.ResponseDto.Recognition_results;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@NoArgsConstructor
@Getter
public class ResponseFoodRecogDto {
    private List<String> names = new ArrayList<>();

    public ResponseFoodRecogDto(List<List<Recognition_results>> collect) {
        for(List<Recognition_results> coll : collect){
            for(Recognition_results recog: coll){
                names.add(recog.getName());
            }

        }
    }
}
