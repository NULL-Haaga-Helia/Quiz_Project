package com.quizzerbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.QuizReviewRepository;
import com.quizzerbackend.domain.Reviews;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizCategoryRepository;
import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuestionRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewsRestController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizCategoryRepository quizCategoryRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizReviewRepository quizReviewRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        quizReviewRepository.deleteAll();
    }

//Returns Not Found status when the quiz with id doesn't exist
@Test
public void getReviewByQuizIdReturnsNotFoundWhenQuizDoesNotExist() throws Exception {

    // Act & Assert
    this.mockMvc.perform(get("/api/quizzes/{quizId}/reviews", 9999))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").value("Quiz with the provided id does not exist"));
}


//Returns Not Found status when the reviews don't exist for quiz with id
@Test
public void getReviewByQuizIdReturnsNotFoundWhenNoReviewsExist() throws Exception {
    // Arrange
    QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
    quizCategoryRepository.save(category);

    Quiz quiz = new Quiz("Quiz 1", "Description 1", "01.12.2024", true, category);
    quizRepository.save(quiz);

    // Act & Assert
    this.mockMvc.perform(get("/api/quizzes/{quizId}/reviews", quiz.getId()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").value("No ratings found for the provided quiz id"));
}


//Returns a list of reviews for quiz with provided id
@Test
public void getReviewsByQuizIdReturnsReviewsWhenQuizExists() throws Exception {
    // Arrange
    QuizCategory quizCategory = new QuizCategory("Category 1", "Description of Category 1");
    quizCategoryRepository.save(quizCategory);  

    Quiz quiz = new Quiz("Sample Quiz", "Description of Sample Quiz", "2024-12-12", true, quizCategory);
    quizRepository.save(quiz);  

    Reviews review1 = new Reviews(quiz, "JohnDoe", 5, "Great quiz!", "2024-12-01");
    Reviews review2 = new Reviews(quiz, "JaneDoe", 4, "Good quiz, but could be better.", "2024-12-02");
    quizReviewRepository.save(review1);
    quizReviewRepository.save(review2);

      // Act & Assert
    this.mockMvc.perform(get("/api/quizzes/{quizId}/reviews", quiz.getId()))
            .andExpect(status().isOk())  
            .andExpect(jsonPath("$", hasSize(2))) 
            .andExpect(jsonPath("$[0].nickname").value("JohnDoe"))
            .andExpect(jsonPath("$[0].rating").value(5))
            .andExpect(jsonPath("$[0].review").value("Great quiz!"))
            .andExpect(jsonPath("$[0].writtenOn").value("2024-12-01"))
            .andExpect(jsonPath("$[1].nickname").value("JaneDoe"))
            .andExpect(jsonPath("$[1].rating").value(4))
            .andExpect(jsonPath("$[1].review").value("Good quiz, but could be better."))
            .andExpect(jsonPath("$[1].writtenOn").value("2024-12-02"));
}
    
}
