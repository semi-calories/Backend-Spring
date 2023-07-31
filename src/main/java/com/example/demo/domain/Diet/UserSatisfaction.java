package com.example.demo.domain.Diet;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(schema = "User_satisfaction")
@ToString
public class UserSatisfaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_satisfation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="food_code")
    private DietList foodCode;
    @Column(name="food_name")
    private String foodName;

    private int satisfaction;

    public UserSatisfaction(User userCode, DietList foodCode, String foodName, int satisfaction) {
        this.userCode = userCode;
        this.foodCode = foodCode;
        this.foodName = foodName;
        this.satisfaction = satisfaction;
    }
}
