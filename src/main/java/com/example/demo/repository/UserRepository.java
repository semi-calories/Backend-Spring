package com.example.demo.repository;


import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {


}
