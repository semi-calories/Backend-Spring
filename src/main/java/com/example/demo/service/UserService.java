package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.example.demo.repository.UserGoalRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;

    /**
     * 유저 단건 검색
     */
    public User findOne(Long userCode) throws Exception{
        Optional<User> findUser = (Optional<User>) userRepository.findById(userCode);
        if(findUser.isPresent()){
            return findUser.get();
        }else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    /**
     * 유저 및 유저 목표 조회
     */
    public UserGoal findUserWithUserGoal(Long userCode) throws Exception{
        Optional<UserGoal> findUserGoal = userGoalRepository.findAllWithUser(userCode);
        if(findUserGoal.isPresent()){
            return findUserGoal.get();
        }else {
            throw new IllegalStateException("존재하지 않는 정보입니다.");
        }
    }

}
