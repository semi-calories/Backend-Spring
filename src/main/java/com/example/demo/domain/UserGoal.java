package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Double kcal;
    @Column(name="user_carbo")
    private Double carbo;
    @Column(name="user_protein")
    private Double protein;
    @Column(name="user_fat")
    private Double fat;

    //==비즈니스 로직==//
    public void change(String userActivity, String userGoal, double goalWeight){
        this.userActivity = userActivity;
        this.goalWeight = goalWeight;
        this.userGoal = userGoal;
    }

    public void harrisBenedict(List<Double> hbList){
        this.kcal = hbList.get(0);
        this.carbo = hbList.get(1);
        this.protein = hbList.get(2);
        this.fat = hbList.get(3);
    }

}
