package com.example.demo.domain.Diet;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "User_diet_prefer")
public class UserDietPrefer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_diet_prefer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    @JsonIgnore
    private User userCode;


    @ManyToOne // (fetch=FetchType.LAZY)
    @JoinColumn(name="prefer_food_code")
    private DietList preferFoodCode;
    @Column(name="prefer_food_name")
    private String preferFoodName;

    public UserDietPrefer(User userCode, DietList preferFoodCode, String preferFoodName) {
        this.userCode = userCode;
        this.preferFoodCode = preferFoodCode;
        this.preferFoodName = preferFoodName;
    }
}
