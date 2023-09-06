package com.example.demo.service;

import com.example.demo.domain.DB.DietImg;

import com.example.demo.domain.User.Diet.DietRecord;
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
    public List<DietImg> findDietImgByMainCategory(List<String> foodMainCategoryList){
       List<DietImg> foodImg = foodMainCategory.stream()
               .map(foodMainCategory -> foodMainCategory)
               .findAllByMainCategory(param);
       List<DietImg> list = new ArrayList<>();
       foodMainCategoryList.forEach(img->{
           Optional<DietImg> mainImg = dietImgRepository.findByMainCategory(img);
          if mainImg.isPresent()
           list.add(mainImg);
       });

       return list;

    }
}
