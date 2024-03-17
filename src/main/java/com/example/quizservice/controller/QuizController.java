package com.example.quizservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizservice.dto.QuestionWrapper;
import com.example.quizservice.dto.QuizDto;
import com.example.quizservice.dto.ResponseDTO;
import com.example.quizservice.service.QuizService;

@RestController
@RequestMapping("quiz/")
public class QuizController {
	
	@Autowired
    QuizService quizService;

    @PostMapping("add")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }
    

    @PostMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<ResponseDTO> responses){
        return quizService.calculateResult(id, responses);
    }


}
