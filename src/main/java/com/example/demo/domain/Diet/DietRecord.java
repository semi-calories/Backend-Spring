package com.example.demo.domain.Diet;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User.User;
import com.example.demo.dto.Record.Request.RequestRecordDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class DietRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diet_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    @JsonIgnore
    private User userCode;


    @Column(name="food_times")
    private int foodTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code")
    @JsonIgnore
    private DietList foodCode;

    @Column(name="eat_date")
    private LocalDateTime eatDate;

    @Column(name="food_weight")
    private Double foodWeight;

    @Column(name="food_name")
    private String foodName;

    @Column(name="food_kcal")
    private Double foodKcal;

    @Column(name="food_carbo")
    private Double foodCarbo;

    @Column(name="food_protein")
    private Double foodProtein;

    @Column(name="food_fat")
    private Double foodFat;


    //== DB 저장용 생성자 ==//

    public DietRecord(RequestRecordDto recordDto, LocalDateTime eatDate, User userCode, DietList foodCode) {
        this.userCode = userCode;
        this.foodCode = foodCode;
        this.eatDate = eatDate;
        this.foodName = foodCode.getFoodName();
        this.foodWeight = recordDto.getFoodWeight();
        this.foodKcal = recordDto.getFoodKcal();
        this.foodCarbo = recordDto.getFoodCarbo();
        this.foodProtein = recordDto.getFoodProtein();
        this.foodFat = recordDto.getFoodFat();
    }

    //==비즈니스 로직==//
    public void change(DietList foodCode, LocalDateTime eatDate, Double foodWeight, Double foodKcal, Double foodCarbo, Double foodProtein, Double foodFat){
        this.foodCode = foodCode;
        this.eatDate = eatDate;
        this.foodName = foodCode.getFoodName();
        this.foodWeight = foodWeight;
        this.foodKcal = foodKcal;
        this.foodCarbo = foodCarbo;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
    }
}
