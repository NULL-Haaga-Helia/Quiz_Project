package com.quizzerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.Question;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;




@SpringBootApplication
public class QuizzerbackendApplication {
	
	private static final Logger log = LoggerFactory.getLogger(QuizzerbackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QuizzerbackendApplication.class, args);
	}
 
	@Bean
	public CommandLineRunner quizDemo(QuizRepository quizRepository, QuestionRepository questionRepository) {
		return (args) -> {
			log.info("save a couple of quizzes");

			quizRepository.save(new Quiz("Quiz 1", "First test quiz", "12.12.2024", true));
			quizRepository.save(new Quiz("Quiz 2", "Second test quiz", "13.12.2024", false));	
			quizRepository.save(new Quiz("Quiz 3", "Third test quiz", "14.12.2023", true));


			log.info("fetch all Quizzes");
			for (Quiz quiz : quizRepository.findAll()) {
				log.info(quiz.toString());
			}
		};
	}
	
}
