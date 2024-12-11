package com.quizzerbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.quizzerbackend.domain.Answer;
import com.quizzerbackend.domain.AnswerRepository;
import com.quizzerbackend.domain.Question;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuizCategoryRepository;
import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.AnswerDTO;
import com.quizzerbackend.domain.UserAnswerRepository;
import com.quizzerbackend.domain.UserAnswer;

@SpringBootTest
@AutoConfigureMockMvc
public class AnswerRestControllerTest {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizCategoryRepository quizCategoryRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        userAnswerRepository.deleteAll(); 
        answerRepository.deleteAll();
        quizRepository.deleteAll();
        quizCategoryRepository.deleteAll();
        questionRepository.deleteAll();
    }

    // Return an empty list of questions when a quiz does not contain any questions
    @Test
    public void createAnswerSavesAnswerForPublishedQuiz() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
        quizCategoryRepository.save(category);

        Quiz quiz = new Quiz("q1", "quiz1", "12.12.2024", true, category);
        quizRepository.save(quiz);

        Question question = new Question("q1", "Easy", quiz);
        questionRepository.save(question);

        Answer answer = new Answer(true, "Answer 1", question);
        answerRepository.save(answer);

        UserAnswer userAnswer = new UserAnswer(answer);
        AnswerDTO answerDTO = new AnswerDTO(userAnswer);

        // Act
        this.mockMvc
                .perform(post("/api/quizzes/" + quiz.getId() + "/questions/" + question.getQuestionId() + "/answers/"
                        + answer.getAnswerId() + "/userAnswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(answerDTO)))

                // Assert
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.answer.isCorrect").value(answer.getIsCorrect()))
                .andExpect(jsonPath("$.answer.text").value(answer.getText()));

        List<UserAnswer> userAnswers = userAnswerRepository.findAll();

        assertEquals(1, userAnswers.size());
    }

    @Test
    public void createAnswerDoesNotSaveAnswerWithoutAnswerOption() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
        quizCategoryRepository.save(category);

        Quiz quiz = new Quiz("q1", "quiz1", "12.12.2024", true, category);
        quizRepository.save(quiz);

        Question question = new Question("q1", "Easy", quiz);
        questionRepository.save(question);

        Answer answer = new Answer(true, "Answer 1", question);
        answerRepository.save(answer);

        AnswerDTO answerDTO = new AnswerDTO();

        // Act
        this.mockMvc
                .perform(post("/api/quizzes/" + quiz.getId() + "/questions/" + question.getQuestionId() + "/answers/"
                        + answer.getAnswerId() + "/userAnswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(answerDTO)))

                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Answer option is required"));

        List<UserAnswer> userAnswers = userAnswerRepository.findAll();

        assertEquals(0, userAnswers.size());
    }

    @Test
    public void createAnswerDoesNotSaveAnswerForNonExistingAnswerOption() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
        quizCategoryRepository.save(category);

        Quiz quiz = new Quiz("q1", "quiz1", "12.12.2024", true, category);
        quizRepository.save(quiz);

        Question question = new Question("q1", "Easy", quiz);
        questionRepository.save(question);

        Answer answer = new Answer(true, "Answer 1", question);
        answerRepository.save(answer);

        UserAnswer userAnswer = new UserAnswer(answer);
        AnswerDTO answerDTO = new AnswerDTO(userAnswer);

        // Act
        this.mockMvc
                .perform(post("/api/quizzes/" + quiz.getId() + "/questions/" + question.getQuestionId()
                        + "/answers/7/userAnswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(answerDTO)))

                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Answer with the provided id does not exist"));

        List<UserAnswer> userAnswers = userAnswerRepository.findAll();

        assertEquals(0, userAnswers.size());
    }

    @Test
    public void createAnswerDoesNotSaveAnswerForUnpublishedQuiz() throws Exception {
        // Arrange
        QuizCategory category = new QuizCategory("Category 1", "Description of Category 1");
        quizCategoryRepository.save(category);

        Quiz quiz = new Quiz("q1", "quiz1", "12.12.2024", false, category);
        quizRepository.save(quiz);

        Question question = new Question("q1", "Easy", quiz);
        questionRepository.save(question);

        Answer answer = new Answer(true, "Answer 1", question);
        answerRepository.save(answer);

        UserAnswer userAnswer = new UserAnswer(answer);
        AnswerDTO answerDTO = new AnswerDTO(userAnswer);

        // Act
        this.mockMvc
                .perform(post("/api/quizzes/" + quiz.getId() + "/questions/" + question.getQuestionId() + "/answers/"
                        + answer.getAnswerId() + "/userAnswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(answerDTO)))

                // Assert
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").value("Quiz is not published"));

        List<UserAnswer> userAnswers = userAnswerRepository.findAll();

        assertEquals(0, userAnswers.size());
    }

}
