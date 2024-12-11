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
import java.util.List;
import static org.hamcrest.Matchers.*;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizCategoryRepository;
import com.quizzerbackend.domain.QuizCategory;


@SpringBootTest
@AutoConfigureMockMvc
public class QuizRestControllerTest {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizCategoryRepository quizCategoryRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        quizRepository.deleteAll();
        quizCategoryRepository.deleteAll();
    }

    // Return an empty list of quizzes
    @Test
    public void getAllQuizzesReturnsEmptyListWhenNoQuizzesExist() throws Exception {
        // Act
        this.mockMvc.perform(get("/api/quizzes"))
        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


    // Return a list of all quizzes from a list with only published quizzes
    @Test
    public void getAllQuizzesReturnsListOfQuizzesWhenPublishedQuizzesExist() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
        quizCategoryRepository.save(category); 

        Quiz publishedQuiz1 = new Quiz("Quiz 1", "Description 1", "01.12.2024", true, category);
        Quiz publishedQuiz2 = new Quiz("Quiz 2", "Description 2", "02.12.2024", true, category);

        quizRepository.saveAll(List.of(publishedQuiz1, publishedQuiz2));

        // Act
        this.mockMvc.perform(get("/api/quizzes"))
        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))  
                .andExpect(jsonPath("$[0].name").value("Quiz 1"))
                .andExpect(jsonPath("$[0].id").value(publishedQuiz1.getId()))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].addedOn").value("01.12.2024"))
                .andExpect(jsonPath("$[0].isPublished").value(true))
                .andExpect(jsonPath("$[0].quizCategory.name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Quiz 2"))
                .andExpect(jsonPath("$[1].id").value(publishedQuiz2.getId()))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].addedOn").value("02.12.2024"))
                .andExpect(jsonPath("$[1].isPublished").value(true))
                .andExpect(jsonPath("$[1].quizCategory.name").value("Category 1"));
    }

    // Return a list of only published quizzes from a list of published and unpublished quizzes
    @Test
    public void getAllQuizzesDoesNotReturnUnpublishedQuizzes() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
        quizCategoryRepository.save(category); 

        Quiz publishedQuiz1 = new Quiz("Quiz 1", "Description 1", "01.12.2024", true, category);
        Quiz publishedQuiz2 = new Quiz("Quiz 2", "Description 2", "02.12.2024", true, category);
        Quiz unpublishedQuiz1 = new Quiz("Unpublished Quiz 1", "Description 3", "03.12.2024", false, category);
        Quiz unpublishedQuiz2 = new Quiz("Unpublished Quiz 2", "Description 4", "04.12.2024", false, category);

        quizRepository.saveAll(List.of(publishedQuiz1, publishedQuiz2, unpublishedQuiz1, unpublishedQuiz2));

        // Act
        this.mockMvc.perform(get("/api/quizzes"))
        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))  
                .andExpect(jsonPath("$[0].name").value("Quiz 1"))
                .andExpect(jsonPath("$[0].id").value(publishedQuiz1.getId()))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].addedOn").value("01.12.2024"))
                .andExpect(jsonPath("$[0].isPublished").value(true))
                .andExpect(jsonPath("$[0].quizCategory.name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Quiz 2"))
                .andExpect(jsonPath("$[1].id").value(publishedQuiz2.getId()))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].addedOn").value("02.12.2024"))
                .andExpect(jsonPath("$[1].isPublished").value(true))
                .andExpect(jsonPath("$[1].quizCategory.name").value("Category 1"));
}


}
