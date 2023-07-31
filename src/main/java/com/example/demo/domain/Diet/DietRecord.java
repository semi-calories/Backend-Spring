package com.example.demo.domain.Diet;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User;
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
    @Column(name="diet_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

    private String goal;

    @Column(name="food_times")
    private int foodTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code")
    private DietList foodCode;

    @Column(name="eat_date")
    private LocalDateTime eatDate;

    @Column(name="food_kcal")
    private Long foodKcal;

    @Column(name="food_carbo")
    private Long foodCarbo;

    @Column(name="food_protein")
    private Long foodProtein;

    @Column(name="food_fat")
    private Long foodFat;
}
