package com.example.demo.repository;

import com.example.demo.domain.Diet.UserSatisfaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSatisfactionRepository extends JpaRepository<UserSatisfaction, Long> {
}
