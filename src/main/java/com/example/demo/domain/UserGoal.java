package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "User_goal")
public class UserGoal extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_goal_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

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

    //==비즈니스 로직==//
    public void change(String userActivity, String userGoal, double goalWeight, Long kcal, Long carbo, Long protein, Long fat){
        this.userActivity = userActivity;
        this.goalWeight = goalWeight;
        this.userGoal = userGoal;
        this.kcal = kcal;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
    }

}
