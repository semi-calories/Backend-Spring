package com.example.demo.domain.Diet;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(schema = "User_diet_dislike")
public class UserDietDislike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_diet_dislike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dislike_food_code")
    private DietList dislikeFoodCode;
    @Column(name="dislike_food_name")
    private String dislikeFoodName;


    public UserDietDislike(User userCode, DietList dislikeFoodCode, String dislikeFoodName) {
        this.userCode = userCode;
        this.dislikeFoodCode = dislikeFoodCode;
        this.dislikeFoodName = dislikeFoodName;
    }


}
