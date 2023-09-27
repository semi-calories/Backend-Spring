package com.example.demo.service;
// Image
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

    // Image

    public List<ResponseRecommendDto> findDietImgByFoodMainCategory(List<RecommendDto> recommendDtoList){
        List<ResponseRecommendDto> DietCategoryImg = new ArrayList<>();
        for(int i=0; i<recommendDtoList.size(); i++){
            //DietImg temp = new DietImg(foodMainCategoryList[i],);
            //String foodMainCategory = foodMainCategoryList.get(i);
            String foodMainCategory = recommendDtoList.get(i).getFoodMainCategory();
            System.out.println("foodMainCategory = " + foodMainCategory);

            Optional<DietImg> tempImg = dietImgRepository.findByFoodMainCategory(foodMainCategory);

            System.out.println("tempImg = " + tempImg);
            if (tempImg.isEmpty()){
                System.out.println(" 없음!!!!!!!!!!!!!!!!!!"  );
                tempImg = dietImgRepository.findByFoodMainCategory("조림류");
            }
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
            System.out.println("추천결과 = \n" + DietCategoryImg.get(i));
        }

       return DietCategoryImg;

    }
}


//우선 for문으로
       /*List<DietImg> foodImg = foodMainCategoryList.stream()
               .map(foodMainCategory -> dietImgRepository.findByMainCategory(foodMainCategory))
               .filter(Optional::isPresent) // 찾은 dietList가 존재하는 것만 filter
               .map(Optional::get) // 존재하면 get
               .collect(Collectors.toList());// 리스트로 리턴
       //        .findAllByMainCategory(param);
       List<DietImg> list = new ArrayList<>();
       foodMainCategoryList.forEach(img->{
           Optional<DietImg> mainImg = dietImgRepository.findByMainCategory(img);
          if mainImg.isPresent()
           list.add(mainImg);
       }
       );
        */