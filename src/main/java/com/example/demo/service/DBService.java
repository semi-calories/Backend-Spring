package com.example.demo.service;

import com.example.demo.domain.DB.DietList;
import com.example.demo.repository.DietListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DBService {

    private final DietListRepository dietListRepository;

    /**
     * 음식 검색
     */
    public DietList findOne(Long foodCode){
        Optional<DietList> findOne = dietListRepository.findById(foodCode);
        if(findOne.isPresent()){
            return findOne.get();
        }else {
            throw new IllegalStateException("존재하지 않는 정보입니다.");
        }
    }

    /**
     * 음식 여러번 검색
     */
    public List<DietList> findByList(List<Long> foodCodeList){
        List<DietList> dietList = new ArrayList<>();
        for (Long foodCode : foodCodeList) {
            Optional<DietList> findDiet = dietListRepository.findById(foodCode);
            if (findDiet.isPresent()){
                dietList.add(findDiet.get());
            }
        }
        if(dietList.size()==0){
            throw new IllegalStateException("인식된 음식의 정보가 없습니다.");
        }
        return dietList;
    }
}
