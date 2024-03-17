package com.example.quizservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizservice.controller.QuizFeignInterface;
import com.example.quizservice.dto.QuestionWrapper;
import com.example.quizservice.dto.ResponseDTO;
import com.example.quizservice.entity.QuizEntity;
import com.example.quizservice.repo.QuizRepository;
import com.example.quizservice.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

//	@Autowired
//	private QuestionRepository questionRepository;

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizFeignInterface quizFeignInterface;

	@Override
	public ResponseEntity<String> createQuiz(String categoryName, String numQuestions, String title) {
		List<Integer> questionIds = quizFeignInterface.getQuestionsForQuiz(categoryName, Integer.parseInt(numQuestions)).getBody();
		QuizEntity quizEntity = new QuizEntity();
		quizEntity.setQuestionIds(questionIds);
		quizEntity.setTitle(title);
		quizRepository.saveAndFlush(quizEntity);
		return ResponseEntity.status(HttpStatus.CREATED).body("success");
	}	
	

	@Override
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) { //get quiz question by quizId(primary key) and we returning only questions and options
//		Optional<QuizEntity> quizop = quizRepository.findById(id);
//		List<QuestionWrapper> questionWrappers = new ArrayList<>();
//		if (quizop.isPresent()) {
//			List<QuestionEntity> questions = quizop.get().getQuestions();
//			for (QuestionEntity question : questions) {
//				questionWrappers.add(new QuestionWrapper(question.getId(), question.getQuestionTitle(),
//						question.getOption1(), question.getOption2(), question.getOption3(), question.getOptions4()));
//			}
//		}
//		return ResponseEntity.ok(questionWrappers);
//		2024-02-25T10:32:55.049+05:30[0;39m [31mERROR[0;39m [35m12812[0;39m [2m---[0;39m [2m[quiz-service] [nio-8102-exec-1][0;39m [2m[0;39m[36mo.a.c.c.C.[.[.[/].[dispatcherServlet]   [0;39m [2m:[0;39m Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: feign.FeignException$NotFound: [404] during [POST] to [http://QUESTION-SERVICE/question/getQuestions///] [QuizFeignInterface#getPostQuestionsById(List)]: [{"timestamp":"2024-02-25T04:55:59.644+00:00","status":404,"error":"Not Found","path":"/question/getQuestions/"}]] with root cause
//
//			feign.FeignException$NotFound: [404] during [POST] to [http://QUESTION-SERVICE/question/getQuestions///] [QuizFeignInterface#getPostQuestionsById(List)]: [{"timestamp":"2024-02-25T04:55:59.644+00:00","status":404,"error":"Not Found","path":"/question/getQuestions/"}]
//				at feign.FeignException.clientErrorStatus(FeignException.java:228) ~[feign-core-13.1.jar:na]
		
		QuizEntity quiz = quizRepository.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = null;
        try {
        	
        	questions = quizFeignInterface.getPostQuestionsById(questionIds);
        }catch(Exception e) {
        	//feign.RetryableException: Read timed out executing POST http://QUESTION-SERVICE/question/getQuestions
        	e.printStackTrace();
        }
        return questions;
	}

	@Override
	public ResponseEntity<Integer> calculateResult(Integer id, List<ResponseDTO> responses) {
		QuizEntity quiz = quizRepository.findById(id).get();
//		List<QuestionEntity> questions = quiz.getQuestions();
		int right = 0;
		int i = 0;
		for (ResponseDTO response : responses) {
			System.out.println(response.getResponse());
//			System.out.println(questions.get(i).getRightAnswer());
//			if (response.getResponse().equals(questions.get(i).getRightAnswer()))
//				right++;
			i++;
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
	

//@Override
//public ResponseEntity<Integer> calculateResult(Integer id, List<ResponseDTO> responses) {
//    QuizEntity quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
//    List<QuestionEntity> questions = quiz.getQuestions();
//
//    long right = IntStream.range(0, Math.min(responses.size(), questions.size()))
//            .filter(i -> responses.get(i).getResponse().equals(questions.get(i).getRightAnswer()))
//            .count();
//
//    return ResponseEntity.ok(Math.toIntExact(right));
//}
	
//
//@Override
//public ResponseEntity<Integer> calculateResult(Integer id, List<ResponseDTO> responses) {
//    QuizEntity quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
//    List<QuestionEntity> questions = quiz.getQuestions();
//
//    long right = responses.stream()
//            .filter(response -> {
//                Optional<QuestionEntity> matchingQuestion = questions.stream()
//                        .filter(question -> question.getId().equals(response.getQuestionId()))
//                        .findFirst();
//                return matchingQuestion.isPresent() && response.getResponse().equals(matchingQuestion.get().getRightAnswer());
//            })
//            .count();
//
//    return ResponseEntity.ok(Math.toIntExact(right));
//}

}
