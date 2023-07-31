package com.example.demo.domain.DB;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

import java.util.prefs.BackingStoreException;

@Entity
@Getter
@ToString
public class DietList {

    @Id
    @Column(name="food_code")
    private Long foodCode;

    @Column(name="food_name")
    private String foodName;

    @Column(name="food_main_category")
    private String foodMainCategory;

    @Column(name="food_detailed_classification")
    private String foodDetailedClassification;

    @Column(name = "food_weight")
    private Long foodWeight;

    @Column(name="food_kcal")
    private Long foodKcal;

    @Column(name="food_carbo")
    private Long foodCarbo;

    @Column(name="food_protein")
    private Long foodProtein;

    @Column(name="food_fat")
    private Long foodFat;
}
