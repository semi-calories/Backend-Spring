package com.example.demo.domain.Diet;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSatisfaction {

    @Id
    @GeneratedValue
    @Column(name="user_diet_dislike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

    private String goal;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="food_code")
    private DietList foodCode;
    @Column(name="food_name")
    private String foodName;

    private int satisfaction;

}
