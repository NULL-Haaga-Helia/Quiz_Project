package com.quizzerbackend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quizzerbackend.domain.Answer;
import com.quizzerbackend.domain.AnswerDTO;
import com.quizzerbackend.domain.AnswerRepository;
import com.quizzerbackend.domain.Question;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuizCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizCategoryRepository quizCategoryRepository;

    // Endpoint for getting all quizzes
    @RequestMapping(value = "/quizzes", method = RequestMethod.GET)
    public @ResponseBody List<Quiz> quizListRest() {
        return quizRepository.findByIsPublished(true);
    }

    // Endpoint for getting the quiz by id
    @RequestMapping(value = "/quizzes/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz with the provided id does not exist"));
        return ResponseEntity.ok(quiz);
    }

    // Endpoint for getting the questions by quiz id
    @RequestMapping(value = "/quizzes/{quizId}/questions", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuestionsForQuiz(@PathVariable Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);

        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No questions found for quiz with id: " + quizId);
        }

        return ResponseEntity.ok(questions);
    }

    // Endpoint for creating the answer by quiz id and question id
    @RequestMapping(value = "/quizzes/{quizId}/questions/{questionId}/answer", method = RequestMethod.POST)
    public ResponseEntity<?> submitAnswer(@PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody AnswerDTO answerDTO) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz with the provided id does not exist"));
        if (!quiz.getIsPublished()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Quiz is not published");
        }

        Answer answer = answerRepository.findById(answerDTO.getAnswerId())
                .orElseThrow(() -> new IllegalArgumentException("Answer with the provided id does not exist"));

        if (answer.getQuestion() == null || !answer.getQuestion().getQuestionId().equals(questionId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The selected answer does not belong to the specified question");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Answer submitted successfully");
    }

    // Endpoint for getting the answers by quiz id and question id
    @GetMapping("/quizzes/{quizId}/answers")
    public ResponseEntity<?> getAnswersByQuizId(@PathVariable Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Quiz with the provided id does not exist");
        }
        List<Answer> answers = answerRepository.findByQuestionQuizId(quizId);

        if (answers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No answers found for the provided quiz id");
        }

        return ResponseEntity.ok(answers);
    }

    // Exercise 15 REST API endpoint for getting all categories
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        List<QuizCategory> categories = quizCategoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No categories found");
        }
        return ResponseEntity.ok(categories);
    }

    // Exercise 16 REST API endpoint for getting the category by id
    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        QuizCategory category = quizCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with the provided id does not exist"));
        return ResponseEntity.ok(category);
    }

    // Exercise 17 REST API endpoint for getting the published quizzes by category id
    @GetMapping("/categories/{categoryId}/quizzes")
    public ResponseEntity<?> getQuizzesByCategoryId(@PathVariable Long categoryId) {
        if (!quizCategoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with the provided id does not exist");
        }
        List<Quiz> quizzes = quizRepository.findByQuizCategoryIdAndIsPublished(categoryId, true);
        if (quizzes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No published quizzes found for the provided category id");
        }
        return ResponseEntity.ok(quizzes);
    }

}
