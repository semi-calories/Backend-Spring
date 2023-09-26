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

    //==생성자==//


    public UserGoal(User userCode) {
        this.userCode = userCode;
    }

    //==비즈니스 로직==//
    public void change(String userActivity, String userGoal, double goalWeight){
        this.userActivity = userActivity;
        this.goalWeight = goalWeight;
        this.userGoal = userGoal;
    }

//    public void harrisBenedict(List<Double> hbList){
//        this.kcal = hbList.get(0);
//        this.carbo = hbList.get(1);
//        this.protein = hbList.get(2);
//        this.fat = hbList.get(3);
//    }

    /**
     *  유저 Harris-Benedict 알고리즘
     */
    public void harrisBenedict(User findUser, Double weight) throws Exception{


        double daliyEnerge = 0;

        // 성별에 따라
        if(findUser.getGender() == Gender.M){
            daliyEnerge = 88.362 + 13.397 * weight + 4.799 * findUser.getHeight() - 5.677 * findUser.getAge();
        }
        else{
            daliyEnerge = 88.362 + 447.593 + 9.24 * weight + 3.098 * findUser.getHeight() - 4.330 * findUser.getAge();
        }

        // 목표에 따라
        if(userGoal.equals("lose")){
            daliyEnerge *= 0.9;
        } else if (userGoal.equals("gain")) {

            daliyEnerge *= 1.1;
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


        this.kcal = daliyEnerge;
        this.carbo = daliyEnerge *0.5 /4 ;
        this.protein = daliyEnerge * 0.3 /4;
        this.fat = daliyEnerge * 0.2/9;
    }

}
