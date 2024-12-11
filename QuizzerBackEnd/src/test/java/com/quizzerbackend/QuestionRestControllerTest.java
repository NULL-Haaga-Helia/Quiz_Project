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
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Question;
import com.quizzerbackend.domain.AnswerRepository;
import com.quizzerbackend.domain.Answer;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionRestControllerTest {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizCategoryRepository quizCategoryRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        quizRepository.deleteAll();
        quizCategoryRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }

     // Return an empty list of questions when a quiz does not contain any questions
     @Test
     public void getQuestionsByQuizIdReturnsEmptyListWhenQuizDoesNotHaveQuestions() throws Exception {
         // Arrange
         QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
         quizCategoryRepository.save(category);
 
         Quiz quiz = new Quiz("Quiz 1", "Description 1", "01.12.2024", true, category);
         quizRepository.save(quiz);
 
         // Act
         this.mockMvc.perform(get("/api/quizzes/{quizId}/questions", quiz.getId()))
         // Assert
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", hasSize(0))); 
     }


// Return a list of questions (for a quiz), containing answer options
@Test
public void getQuestionsByQuizIdReturnsListOfQuestionsWhenQuizHasQuestions() throws Exception {
    // Arrange
    QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
    quizCategoryRepository.save(category);

    Quiz quiz = new Quiz("Quiz 1", "Description 1", "01.12.2024", true, category);
    quizRepository.save(quiz);

    Question question1 = new Question("q1", "Easy", quiz);
    Answer answer1a = new Answer(true, "a1", question1);
    Answer answer1b = new Answer(false, "a2", question1);
    Answer answer1c = new Answer(false, "a3", question1);

    Question question2 = new Question("q2", "Difficult", quiz);
    Answer answer2a = new Answer(true, "a1", question2);
    Answer answer2b = new Answer(false, "a2", question2);

    questionRepository.saveAll(List.of(question1, question2));  
    answerRepository.saveAll(List.of(answer1a, answer1b, answer1c, answer2a, answer2b));  

    // Act
    this.mockMvc.perform(get("/api/quizzes/{quizId}/questions", quiz.getId()))
    // Assert
            .andExpect(status().isOk())  
            .andExpect(jsonPath("$", hasSize(2)))  
            // Question 1
            .andExpect(jsonPath("$[0].questionId").value(question1.getQuestionId()))
            .andExpect(jsonPath("$[0].questionText").value("q1"))
            .andExpect(jsonPath("$[0].difficulty").value("Easy"))
            .andExpect(jsonPath("$[0].answers", hasSize(3))) 
            .andExpect(jsonPath("$[0].answers[0].answerId").value(answer1a.getAnswerId()))
            .andExpect(jsonPath("$[0].answers[0].text").value("a1"))
            .andExpect(jsonPath("$[0].answers[0].isCorrect").value(true))
            .andExpect(jsonPath("$[0].answers[1].answerId").value(answer1b.getAnswerId()))
            .andExpect(jsonPath("$[0].answers[1].text").value("a2"))
            .andExpect(jsonPath("$[0].answers[1].isCorrect").value(false))
            .andExpect(jsonPath("$[0].answers[2].answerId").value(answer1c.getAnswerId()))
            .andExpect(jsonPath("$[0].answers[2].text").value("a3"))
            .andExpect(jsonPath("$[0].answers[2].isCorrect").value(false))
            // Question 2
            .andExpect(jsonPath("$[1].questionId").value(question2.getQuestionId()))
            .andExpect(jsonPath("$[1].questionText").value("q2"))
            .andExpect(jsonPath("$[1].difficulty").value("Difficult"))
            .andExpect(jsonPath("$[1].answers", hasSize(2)))  
            .andExpect(jsonPath("$[1].answers[0].answerId").value(answer2a.getAnswerId()))
            .andExpect(jsonPath("$[1].answers[0].text").value("a1"))
            .andExpect(jsonPath("$[1].answers[0].isCorrect").value(true))
            .andExpect(jsonPath("$[1].answers[1].answerId").value(answer2b.getAnswerId()))
            .andExpect(jsonPath("$[1].answers[1].text").value("a2"))
            .andExpect(jsonPath("$[1].answers[1].isCorrect").value(false));
}


// Send a request for listing questions of a quiz that doesn't exist. Return appropriate HTTP status.
@Test
public void getQuestionsByQuizIdReturnsErrorWhenQuizDoesNotExist() throws Exception {

    // Act
    this.mockMvc.perform(get("/api/quizzes/{quizId}/questions", 9999))
    // Assert
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Quiz with the provided id does not exist"));
}


}

