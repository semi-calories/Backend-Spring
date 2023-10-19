package com.example.demo.repository;

import com.example.demo.domain.DB.DietImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DietImgRepository extends JpaRepository<DietImg, Long> {
    /**
     * diet img 조회 by food MainCategory
     */
    Optional<DietImg> findByFoodMainCategory(@Param("foodMainCategory") String foodMainCategory);
}

