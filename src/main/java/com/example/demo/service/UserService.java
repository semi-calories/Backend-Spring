package com.example.demo.service;

import com.example.demo.domain.User.Gender;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.User.Request.RequestUserUpdateDto;
import com.example.demo.repository.UserGoalRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;


    /**
     * 유저 기본 정보 수정
     */
    @Transactional
    public Long userUpdate(RequestUserUpdateDto requestUserInfoUpdateDto) throws Exception{

        User findUser = findOne(requestUserInfoUpdateDto.getUserCode());

        findUser.change(
                requestUserInfoUpdateDto.getName(),
                requestUserInfoUpdateDto.getEmail(),
                requestUserInfoUpdateDto.getAge(),
                requestUserInfoUpdateDto.getGender(),
                requestUserInfoUpdateDto.getHeight(),
                requestUserInfoUpdateDto.getWeight()
        );

        return findUser.getUserCode();
    }

    /**
     * 유저 목표 정보 수정
     */
    @Transactional
    public Long userGoalUpdate(RequestUserUpdateDto requestUserUpdateDto) throws Exception {

        UserGoal findGoal = findUserWithUserGoal(requestUserUpdateDto.getUserCode());

        findGoal.change(
                requestUserUpdateDto.getUserActivity(),
                requestUserUpdateDto.getUserGoal(),
                requestUserUpdateDto.getGoalWeight()
        );


        return findGoal.getUserCode().getUserCode();
    }



    /**
     * 유저 단건 검색
     */
    public User findOne(Long userCode) throws Exception{

        return userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

    }

    /**
     * 유저 및 유저 목표 조회
     */
    public UserGoal findUserWithUserGoal(Long userCode) throws Exception{

        return userGoalRepository.findAllWithUser(userCode)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 정보입니다.") );
    }

    /**
     * 이미 있는 유저에 HB 알고리즘 돌려 DB에 저장
      */
    @Transactional
    public void changeHarrisBenedict(Long userCode) throws Exception{
        User findUser = findOne(userCode);
        UserGoal findGoal = findUserWithUserGoal(userCode);

        if ( findUser.getAge() != null && findUser.getHeight() != null && findUser.getWeight() !=null
                && findGoal.getUserGoal() != null && findGoal.getUserActivity()!=null){

            List<Double> doubles = harrisBenedict(findUser, findGoal);
            findGoal.harrisBenedict(doubles);
        }

    }

    /**
     *  유저 Harris-Benedict 알고리즘
     */
    public List<Double> harrisBenedict(User findUser, UserGoal findGoal) throws Exception{


        double daliyEnerge = 0;

        // 성별에 따라
        if(findUser.getGender() == Gender.M){
            daliyEnerge = 88.362 + 13.397 * findUser.getWeight() + 4.799 * findUser.getHeight() - 5.677 * findUser.getAge();
        }
        else{
            daliyEnerge = 88.362 + 447.593 + 9.24 * findUser.getWeight() + 3.098 * findUser.getHeight() - 4.330 * findUser.getAge();
        }

        // 활동계수에 따라
        switch (findGoal.getUserActivity()) {
            case "less":
                daliyEnerge *= 1.3;
                break;
            case "normal":
                daliyEnerge *= 1.5;
                break;
            default:
                daliyEnerge *= 1.7;
        }

        // 목표에 따라
        if(findGoal.getUserGoal()=="lose"){
            daliyEnerge *= 0.9;
        } else if (findGoal.getUserGoal()=="gain") {
            daliyEnerge *= 1.1;
        }

        return Arrays.asList(daliyEnerge, daliyEnerge *0.5 /4 , daliyEnerge * 0.3 /4, daliyEnerge * 0.2/9);

    }

}
