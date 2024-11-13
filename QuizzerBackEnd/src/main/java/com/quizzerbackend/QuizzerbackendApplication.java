package com.quizzerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.quizzerbackend.domain.QuizRepository;
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
			var quizzes = quizRepository.findAll();
			if (quizzes.isEmpty()) {
				log.info("save a couple of quizzes");

				quizRepository.save(new Quiz("Biology", "Learn fundamental concepts in biology", "12.12.2024", true));
				quizRepository.save(new Quiz("History", "Learn significant events, figures, and empires that have shaped world history.", "13.12.2024", false));	
				quizRepository.save(new Quiz("Literature", "Learn about famous authors, classic literature, and iconic literary characters.", "14.12.2023", false));


				log.info("fetch all Quizzes");
				for (Quiz quiz : quizRepository.findAll()) {
					log.info(quiz.toString());
				}
			}
		};
	
	}
	
}
