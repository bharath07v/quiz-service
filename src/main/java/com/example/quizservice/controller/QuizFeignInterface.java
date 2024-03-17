package com.example.quizservice.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quizservice.dto.QuestionWrapper;
import com.example.quizservice.dto.ResponseDTO;

@FeignClient("QUESTION-SERVICE")
public interface QuizFeignInterface {


	@GetMapping("question/generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
			@RequestParam Integer numQuestions) ;
	
//	@GetMapping("getQuestions///[questionIds]")
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable String questionIds) {

	
	
//	@GetMapping("getQuestions///{questionIds}")
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable String questionIds) {
//		System.out.println(questionIds);
//		String[] questionStringArray = questionIds.replaceAll("[]", "").split(",");

	@GetMapping("question/getQuestions///{questionIds}")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable String questionIds) ;
	@PostMapping("question/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getPostQuestionsById(@RequestBody List<Integer> questionsIds);
//	feign.FeignException$NotFound: [404] during [GET] to [http://QUESTION-SERVICE/question/getQuestions///] [QuizFeignInterface#getPostQuestionsById(List)]: [{"timestamp":"2024-02-25T05:06:43.436+00:00","status":404,"error":"Not Found","path":"/question/getQuestions/"}]
	@PostMapping("question/getScores")
	public ResponseEntity<Integer> getScores(@RequestBody List<ResponseDTO> responses);
	

}
