package com.quizzerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.Difficulty;
import com.quizzerbackend.domain.DifficultyRepository;
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
	public CommandLineRunner quizDemo(QuizRepository repository, DifficultyRepository difficultyRepository, QuestionRepository questionRepository) {
		return (args) -> {
			log.info("save a couple of");

			questionRepository.save(new Question("Question description"));


			difficultyRepository.save(new Difficulty("Easy", "comments"));
			difficultyRepository.save(new Difficulty("Medium", "comments"));
			difficultyRepository.save(new Difficulty("Hard", "comments"));


			repository.save(new Quiz("Quiz1", "test", "12.12.2024", true, difficultyRepository.findByLevel("Easy").get(0)));
			repository.save(new Quiz("Quiz2", "second quiz", "2024", false, difficultyRepository.findByLevel("Medium").get(0)));	
			repository.save(new Quiz("Quiz3", "third one", "2023", true, difficultyRepository.findByLevel("Hard").get(0)));
			
			
			log.info("fetch all Quizzes");
			for (Quiz quiz : repository.findAll()) {
				log.info(quiz.toString());
			}
		};
	}
	
}
