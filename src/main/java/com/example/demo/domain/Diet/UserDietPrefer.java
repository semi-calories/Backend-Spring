package com.example.demo.domain.Diet;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UserDietPrefer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="user_diet_prefer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

    private String goal;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="prefer_food_code")
    private DietList preferFoodCode;
    @Column(name="prefer_food_name")
    private String preferFoodName;
    @Column(name="prefer_food_kcal")
    private Long preferFoodKcal;

}
