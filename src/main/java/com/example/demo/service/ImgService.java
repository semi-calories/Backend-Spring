package com.example.demo.service;
// Image
import com.example.demo.domain.DB.DietImg;

import com.example.demo.domain.User.Diet.DietRecord;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImgService {

    private final DietImgRepository dietImgRepository;
    /**
     * 음식 대분류 이름으로 아이콘 이미지 검색
     */

    // Image

    public List<DietImg> findDietImgByFoodMainCategory(List<String> foodMainCategoryList){
        List<DietImg> DietCategoryImg = new ArrayList<>();
        for(int i=0; i<foodMainCategoryList.size(); i++){
            //DietImg temp = new DietImg(foodMainCategoryList[i],);
            String foodMainCategory = foodMainCategoryList.get(i);
            System.out.println("foodMainCategory = " + foodMainCategory);
            Optional<DietImg> tempImg = dietImgRepository.findByFoodMainCategory(foodMainCategory);
            if (tempImg.isEmpty()){
                System.out.println(" 없음!!!!!!!!!!!!!!!!!!"  );
            }
            DietCategoryImg.add(tempImg.get());
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