package com.example.demo.controller;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.Recommend.FastAPI.RequestRecommendAPIDto;
import com.example.demo.dto.Recommend.FastAPI.ResponseRecommendAPIDto;
import com.example.demo.dto.Recommend.Request.RequestRecommendDto;
import com.example.demo.dto.Recommend.Response.RecommendDto;
import com.example.demo.dto.Recommend.Response.ResponseRecommendDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public ResponseRecommendDto requestRecommend(@RequestBody RequestRecommendDto requestRecommendDto) throws Exception {
        // DB에서 해당 정보 가져옴
        // 유저 목표 및 유저 조회
        UserGoal user = userService.findUserWithUserGoal(requestRecommendDto.getUserCode());

        // 유저 선호, 비선호, 기록 조회
        List<UserDietPrefer> preferDiet = dietService.findPreferByUserCode(requestRecommendDto.getUserCode());
        List<UserDietDislike> dislikeDiet = dietService.findDislikeByUserCode(requestRecommendDto.getUserCode());
        List<DietRecord> dietRecords = dietService.findDietRecordByUserCodeAndDate(requestRecommendDto.getUserCode(), LocalDate.now());


        // FASTAPI 서버에 api 요청
        RequestRecommendAPIDto requestRecommendAPIDto =
                new RequestRecommendAPIDto(user, requestRecommendDto.getEatTimes(), preferDiet, dislikeDiet, dietRecords);

        ResponseRecommendAPIDto responseAPIDto = fastApiFeign.requestRecommend(requestRecommendAPIDto);

        // FASTAPI 응답 DTO로 list별로 음식 접근가능케 함 (fast api: 인덱스별로 접근)
        List<RecommendDto> recommendDtoList = IntStream.range(0, responseAPIDto.getFoodCodeList().size()) // 음식 추천 수만큼 반복
                .mapToObj(i -> new RecommendDto(
                        responseAPIDto.getFoodCodeList().get(i),
                        responseAPIDto.getFoodNameList().get(i),
                        responseAPIDto.getFoodMainCategoryList().get(i),
                        responseAPIDto.getFoodDetailedClassificationList().get(i),
                        responseAPIDto.getFoodWeightList().get(i),
                        responseAPIDto.getFoodKcalList().get(i),
                        responseAPIDto.getFoodCarbonList().get(i),
                        responseAPIDto.getFoodProteinList().get(i),
                        responseAPIDto.getFoodFatList().get(i)
                ))
                .collect(Collectors.toList());

        // 응답 DTO 생성
        ResponseRecommendDto response = new ResponseRecommendDto(recommendDtoList);

        // return 결과;
        return response;
    }
}
