package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserGoal extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="user_goal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

    @Column(name="prefer_food_code")
    private Long preferFoodCode;

    @Column(name="dislike_food_code")
    private Long dislikeFoodCode;

    @Column(name="user_activity")
    private String userActivity;

    @Column(name="goal_weight")
    private double goalWeight;

    @Column(name="user_goal")
    private String userGoal;

    @Column(name="user_kcal")
    private Long kcal;
    @Column(name="user_carbo")
    private Long carbo;
    @Column(name="user_protein")
    private Long protein;
    @Column(name="user_fat")
    private Long fat;

}
