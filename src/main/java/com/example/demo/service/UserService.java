package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.example.demo.dto.User.Request.RequestUserGoalUpdateDto;
import com.example.demo.dto.User.Request.RequestUserInfoUpdateDto;
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
     * 유저 정보 저장(=회원가입) - 유저 기본 정보 및 목표 저장
     */


    /**
     * 유저 기본 정보 수정
     */
    @Transactional
    public Long userUpdate(RequestUserInfoUpdateDto requestUserInfoUpdateDto) throws Exception{

        User findUser = findOne(requestUserInfoUpdateDto.getUserCode());

        findUser.change(
                requestUserInfoUpdateDto.getName(),
                requestUserInfoUpdateDto.getEmail(),
                requestUserInfoUpdateDto.getAge(),
                requestUserInfoUpdateDto.getGender(),
                requestUserInfoUpdateDto.getPhone(),
                requestUserInfoUpdateDto.getHeight(),
                requestUserInfoUpdateDto.getWeight()
        );

        return findUser.getUserCode();
    }

    /**
     * 유저 목표 정보 수정
     */
    @Transactional
    public Long userGoalUpdate(RequestUserGoalUpdateDto requestUserGoalUpdateDto) throws Exception {

        UserGoal findGoal = findUserWithUserGoal(requestUserGoalUpdateDto.getUserCode());

        findGoal.change(
                requestUserGoalUpdateDto.getUserActivity(),
                requestUserGoalUpdateDto.getUserGoal(),
                requestUserGoalUpdateDto.getGoalWeight(),
                requestUserGoalUpdateDto.getKcal(),
                requestUserGoalUpdateDto.getCarbo(),
                requestUserGoalUpdateDto.getProtein(),
                requestUserGoalUpdateDto.getFat()
        );

        System.out.println("업데이트 완료 : "+findGoal);

        return findGoal.getUserCode().getUserCode();
    }



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
