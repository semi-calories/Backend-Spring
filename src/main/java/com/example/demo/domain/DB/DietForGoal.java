package com.example.demo.domain.DB;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DietForGoal extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="diet_for_goal_id")
    private Long id;

    private String goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code")
    private DietList foodCode;

    @Column(name="food_name")
    private String foodName;

    @Column(name="food_kcal")
    private Long kcal;
    @Column(name="food_carbo")
    private Long carbo;
    @Column(name="food_protein")
    private Long protein;
    @Column(name="food_fat")
    private Long fat;

}
