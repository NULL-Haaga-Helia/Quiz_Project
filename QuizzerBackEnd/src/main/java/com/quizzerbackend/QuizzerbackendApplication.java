package com.quizzerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.quizzerbackend.domain.QuestionRepository;

import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizRepository;

import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuizCategoryRepository;

import com.quizzerbackend.domain.QuizRating;
import com.quizzerbackend.domain.QuizRatingRepo;





@SpringBootApplication
public class QuizzerbackendApplication {
	
	private static final Logger log = LoggerFactory.getLogger(QuizzerbackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QuizzerbackendApplication.class, args);
	}
 
	@Bean
	public CommandLineRunner quizDemo(QuizRepository quizRepository, QuestionRepository questionRepository, QuizCategoryRepository quizCategoryRepository, QuizRatingRepo quizRatingRepo) {
		return (args) -> {

			var quizzes = quizRepository.findAll();
			var quizCategories = quizCategoryRepository.findAll();

			if (quizCategories.isEmpty()) {
				log.info("save a couple of quiz categories");

				QuizCategory c1 = new QuizCategory("Science", "Learn about the world around you");
				QuizCategory c2 = new QuizCategory("History", "Learn about the past");
				QuizCategory c3 = new QuizCategory("Literature", "Learn about the world of books");

				quizCategoryRepository.save(c1);
				quizCategoryRepository.save(c2);
				quizCategoryRepository.save(c3);
			}

			if (quizzes.isEmpty()) {
				log.info("save a couple of quizzes");

				Quiz q1 = new Quiz("Biology", "Learn fundamental concepts in biology", "12.12.2024", true, quizCategoryRepository.findByName("Science").get(0));
				Quiz q2 = new Quiz("History", "Learn significant events, figures, and empires that have shaped world history.", "13.12.2024", false, quizCategoryRepository.findByName("History").get(0));
				Quiz q3 = new Quiz("Literature", "Learn about famous authors, classic literature, and iconic literary characters.", "14.12.2024", false, quizCategoryRepository.findByName("Literature").get(0));

				quizRepository.save(q1);
				quizRepository.save(q2);	
				quizRepository.save(q3);

				quizRatingRepo.save(new QuizRating(quizRepository.findByName("Biology").get(0), "user1", 5, "Great quiz!", "12.12.2024"));
				quizRatingRepo.save(new QuizRating(quizRepository.findByName("Biology").get(0), "user2", 4, "Good quiz!", "12.12.2024"));
				quizRatingRepo.save(new QuizRating(quizRepository.findByName("Biology").get(0), "user3", 3, "Okay quiz!", "12.12.2024"));

				log.info("fetch all Quizzes");
				for (Quiz quiz : quizRepository.findAll()) {
					log.info(quiz.toString());
				}
			}
		};
	
	}
	
}
