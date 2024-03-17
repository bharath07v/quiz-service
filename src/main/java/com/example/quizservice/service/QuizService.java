package com.example.quizservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.quizservice.dto.QuestionWrapper;
import com.example.quizservice.dto.ResponseDTO;

public interface QuizService {

	ResponseEntity<String> createQuiz(String categoryName, String numQuestions, String title);

	ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id);

	ResponseEntity<Integer> calculateResult(Integer id, List<ResponseDTO> responses);

}
