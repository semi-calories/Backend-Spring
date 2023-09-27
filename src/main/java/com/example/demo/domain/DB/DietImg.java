package com.example.demo.domain.DB;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DietImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_main_category_id")
    private Long id;

    @Column(name="food_main_category")
    private String foodMainCategory;

    @Column(name="food_image")
    private String foodImage;

}
