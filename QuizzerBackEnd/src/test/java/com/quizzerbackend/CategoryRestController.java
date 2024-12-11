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

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.QuizReviewRepository;
import com.quizzerbackend.domain.QuizCategoryRepository;
import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.AnswerRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryRestController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizCategoryRepository quizCategoryRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuizReviewRepository quizReviewRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        quizCategoryRepository.deleteAll();
    }

    //Returns the category  when a category with the specified id exists
    @Test
    public void getCategoryByIdReturnsCategoryWhenCategoryExists() throws Exception {
    // Arrange
    QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
    quizCategoryRepository.save(category);

    // Act & Assert
    this.mockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())  
            .andExpect(jsonPath("$.name").value("Category 1"))
            .andExpect(jsonPath("$.description").value("Description of Category 1"));
    }


//Returns not found status when the Category with the id doen't exist 
    @Test
    public void getCategoryByIdReturnsNotFoundWhenCategoryDoesNotExist() throws Exception {
        // Act & Assert
        this.mockMvc.perform(get("/api/categories/{id}", 9999)) 
                .andExpect(status().isNotFound());
    }


//Returns a category by id with an empty description 
    @Test
    public void getCategoryByIdReturnsCategoryIfDescriptionIsEmpty() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 2", "");
        quizCategoryRepository.save(category);

        // Act & Assert
        this.mockMvc.perform(get("/api/categories/{id}", category.getId()))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.name").value("Category 2"))
                .andExpect(jsonPath("$.description").value(""));
    }

    
}
