package com.example.demo.service;

import com.example.demo.domain.DB.DietList;
import com.example.demo.repository.DietListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
