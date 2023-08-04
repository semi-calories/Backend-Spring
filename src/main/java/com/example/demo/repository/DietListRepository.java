package com.example.demo.repository;

import com.example.demo.domain.DB.DietList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietListRepository extends JpaRepository<DietList, Long> {
}
