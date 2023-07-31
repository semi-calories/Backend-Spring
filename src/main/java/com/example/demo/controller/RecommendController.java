package com.example.demo.controller;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.example.demo.dto.Recommend.FastAPI.RequestRecommendAPIDto;
import com.example.demo.dto.Recommend.FastAPI.ResponseRecommendAPIDto;
import com.example.demo.dto.Recommend.Request.RequestRecommendDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {

    private final FastApiFeign fastApiFeign;
    private final UserService userService;
    private final DietService dietService;

    /**
     * 음식 추천 요청 받는 api
     */
    @PostMapping("/request")
    public ResponseRecommendAPIDto requestRecommend(@RequestBody RequestRecommendDto requestRecommendDto) throws Exception {
        // DB에서 해당 유저 정보 가져옴 (유저 없으면 exception 터지는데, 처리 필요)
        // 유저 목표 및 유저 조회
        UserGoal user = userService.findUserWithUserGoal(requestRecommendDto.getUserCode());
        log.info("########## user = {}",user);
        // 유저 선호 음식 조회(비선호도 해야함!)
        List<UserDietPrefer> preferDiet = dietService.findPreferByUserCode(requestRecommendDto.getUserCode());
        List<UserDietDislike> dislikeDiet = dietService.findDislikeByUserCode(requestRecommendDto.getUserCode());

        List<DietRecord> dietRecords = dietService.findDietRecordByUserCode(requestRecommendDto.getUserCode(), now());


        // FASTAPI 서버에 api 요청
        RequestRecommendAPIDto requestRecommendAPIDto =
                new RequestRecommendAPIDto(user, requestRecommendDto.getEatTimes(), preferDiet, dislikeDiet, dietRecords);

        ResponseRecommendAPIDto response = fastApiFeign.requestRecommend(requestRecommendAPIDto);


        // return 결과;
        return response;
    }
}
