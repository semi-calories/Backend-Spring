package com.example.demo.service;

import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.repository.PreferDietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietService {

    private final PreferDietRepository preferRepository;

    /**
     * prefer diet 조회 by user code
     */
    public List<UserDietPrefer> findPreferByUserCode(Long userCode) throws Exception{
        List<UserDietPrefer> preferDietList = preferRepository.findByUserCode(userCode);
        return preferDietList;
    }
}
