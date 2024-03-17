package com.example.quizservice.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class QuizEntity {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String title;

//	    @ManyToMany//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Association 'com.example.quizservice.entity.QuizEntity.questions' targets the type 'java.lang.Integer' which is not an '@Entity' type
//	    Caused by: org.hibernate.AnnotationException: Association 'com.example.quizservice.entity.QuizEntity.questions' targets the type 'java.lang.Integer' which is not an '@Entity' type
	    @ElementCollection
	    private List<Integer> questionIds;
}
