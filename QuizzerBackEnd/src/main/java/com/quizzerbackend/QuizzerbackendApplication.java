package com.quizzerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.Quiz;




@SpringBootApplication
public class QuizzerbackendApplication {
	
	private static final Logger log = LoggerFactory.getLogger(QuizzerbackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QuizzerbackendApplication.class, args);
	}
 
	@Bean
	public CommandLineRunner quizDemo(QuizRepository repository) {
		return (args) -> {
			log.info("save a couple of");
			repository.save(new Quiz("Quiz1", "test", "12.12.2024", true));
			repository.save(new Quiz("Quiz2", "second quiz", "2024", false));	
			repository.save(new Quiz("Quiz3", "third one", "2023", true));
			
			log.info("fetch all students");
			for (Quiz quiz : repository.findAll()) {
				log.info(quiz.toString());
			}
		};
	}
	
}
