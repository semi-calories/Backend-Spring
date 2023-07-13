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
public class SatisfactionRecommendDiet extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="satisfaction_recommend_diet_id")
    private Long id;

    private String goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="food_code")
    private DietList foodCode;

    @Column(name="food_name")
    private String foodName;
}
