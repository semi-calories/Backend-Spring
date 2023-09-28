package com.example.demo.service;
import com.example.demo.domain.DB.DietImg;

import com.example.demo.dto.Recommend.Response.RecommendDto;
import com.example.demo.dto.Recommend.Response.ResponseRecommendDto;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImgService {

    private final DietImgRepository dietImgRepository;

    /**
     * 음식 대분류 이름으로 아이콘 이미지 검색
     */
    public List<ResponseRecommendDto> findDietImgByFoodMainCategory(List<RecommendDto> recommendDtoList){
        List<ResponseRecommendDto> DietCategoryImg = new ArrayList<>();
        for(int i=0; i<recommendDtoList.size(); i++){
            // 음식 대분류 정보로 이미지 url 받아오기
            String foodMainCategory = recommendDtoList.get(i).getFoodMainCategory();
            Optional<DietImg> tempImg = dietImgRepository.findByFoodMainCategory(foodMainCategory);

            // 만약 이미지가 DB에 없으면 다른 이미지로 대체
            if (tempImg.isEmpty()){
                tempImg = dietImgRepository.findByFoodMainCategory("조림류");
            }

            // DTO에 이미지 정보도 같이 저장
            DietCategoryImg.add(new ResponseRecommendDto(
                    recommendDtoList.get(i).getFoodCode(),
                    recommendDtoList.get(i).getFoodName(),
                    recommendDtoList.get(i).getFoodMainCategory(),
                    tempImg.get().getFoodImage(),
                    recommendDtoList.get(i).getFoodDetailedClassification(),
                    recommendDtoList.get(i).getFoodWeight(),
                    recommendDtoList.get(i).getFoodKcal(),
                    recommendDtoList.get(i).getFoodCarbon(),
                    recommendDtoList.get(i).getFoodProtein(),
                    recommendDtoList.get(i).getFoodFat()
            ));
        }

       return DietCategoryImg;
    }
}