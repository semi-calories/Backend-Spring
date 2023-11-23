package com.example.demo.domain.User;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "User_goal")
public class UserGoal extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_goal_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

    @Column(name="user_activity")
    private String userActivity;

    @Column(name="goal_weight")
    private Double goalWeight;

    @Column(name="user_goal")
    private String userGoal;

    @Column(name="goal_period")
    private int goalPeriod;

    @Column(name="user_kcal")
    private Double kcal;
    @Column(name="user_carbo")
    private Double carbo;
    @Column(name="user_protein")
    private Double protein;
    @Column(name="user_fat")
    private Double fat;


    //==생성자==//


    public UserGoal(User userCode) {
        this.userCode = userCode;
    }

    //==비즈니스 로직==//
    public void change(String userActivity, String userGoal, double goalWeight, int goalPeriod){
        this.userActivity = userActivity;
        this.goalWeight = goalWeight;
        this.userGoal = userGoal;
        this.goalPeriod = goalPeriod;
    }

    public void predictWeightChange( double goalWeight,int goalPeriod ){
        this.goalWeight = goalWeight;
        this.goalPeriod = goalPeriod;
    }


    /**
     *  유저 Harris-Benedict 알고리즘
     */
    public void harrisBenedict(User findUser, Double weight, Double goalKcal){


        double daliyEnerge = 0;

        // 성별에 따라
        if(findUser.getGender() == Gender.M){
            daliyEnerge = 10 * weight + 6.25 * findUser.getHeight() - 5 * findUser.getAge() +5;
        }
        else{
            daliyEnerge =  10 * weight + 6.25 * findUser.getHeight() - 5 * findUser.getAge() -161;
        }

        // 활동계수에 따라
        switch (userActivity) {
            case "less":
                daliyEnerge *= 1.3;
                break;
            case "normal":
                daliyEnerge *= 1.5;
                break;
            default:
                daliyEnerge *= 1.7;
                break;
        }

        // 목표에 따라
        if(userGoal.equals("lose")){
            daliyEnerge -= goalKcal;
        } else if (userGoal.equals("gain")) {
            daliyEnerge += goalKcal;
        }




        this.kcal = daliyEnerge;
        this.carbo = daliyEnerge *0.5 /4 ;
        this.protein = daliyEnerge * 0.3 /4;
        this.fat = daliyEnerge * 0.2/9;
    }

}
