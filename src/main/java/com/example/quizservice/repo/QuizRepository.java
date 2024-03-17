package com.example.quizservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quizservice.entity.QuizEntity;

public interface QuizRepository extends JpaRepository<QuizEntity, Integer>{

}
