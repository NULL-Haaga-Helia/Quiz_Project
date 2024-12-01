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
import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuizCategoryRepository;
import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.UserAnswer;
import com.quizzerbackend.domain.UserAnswerRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.List;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Message", description = "Operations for accessing and managing quizzes")
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

     @Autowired
    private QuizCategoryRepository quizCategoryRepository;


       //Endpoint for getting all published quizzes
    @Operation(
        summary = "Get a all published quizzes",
        description = "Returns the list of published quizzes"
    )

    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successful operation"),
    @ApiResponse(responseCode = "404", description = "No published quizzes")
})

    @RequestMapping(value = "/quizzes", method = RequestMethod.GET)
    public @ResponseBody List<Quiz> quizListRest() {
        return quizRepository.findByIsPublished(true);
    }

    //Endpoint for getting the quiz by id
    @Operation(
        summary = "Get quizzes by id",
        description = "Returns the quiz with the provided id"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Quiz with the provided id does not exist")
    })

    @RequestMapping(value = "/quizzes/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Quiz with the provided id does not exist"));
        return ResponseEntity.ok(quiz);
    }


    //Endpoint for getting the questions by quiz id
    @Operation(
        summary = "Get questions by quiz id",
        description = "Returns the list of questions for the quiz with the provided id"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Question with the provided id does not exist for the quiz with the provided id")
    })

    @RequestMapping(value = "/quizzes/{quizId}/questions", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuestionsForQuiz(@PathVariable Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(questions);
    }


//Endpoint for creating the UserAnswer by quiz id and question id

@Operation(
    summary = "Post answer by question id and quiz id",
    description = "Creates an answer for the question and quiz with the provided ids"
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Successful operation"),
    @ApiResponse(responseCode = "404", description = "Answer with the provided id does not exist for the quiz with the provided id"),
    @ApiResponse(responseCode = "400", description = "POST request is not valid")
})
@RequestMapping(value = "/quizzes/{quizId}/questions/{questionId}/answer/{answerId}", method = RequestMethod.POST)
public ResponseEntity<?> submitAnswer(@PathVariable Long quizId,
                                      @PathVariable Long questionId,
                                      @PathVariable Long answerId,
                                      @RequestBody AnswerDTO answerDTO) {

    Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new IllegalArgumentException("Quiz with the provided id does not exist"));
    if (!quiz.getIsPublished()) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Quiz is not published");
    }


    Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new IllegalArgumentException("Question with the provided id does not exist"));


    Answer answer = answerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("Answer with the provided id does not exist"));

    if (answer.getQuestion() == null || !answer.getQuestion().getQuestionId().equals(questionId)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The selected answer does not belong to the specified question");
    }

    UserAnswer userAnswer = new UserAnswer(answer, question);
    userAnswerRepository.save(userAnswer);
    

    AnswerDTO savedAnswerDTO = new AnswerDTO(userAnswer);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAnswerDTO);
}


       //Endpoint for getting the answers by quiz id and question id
       @Operation(
        summary = "Get answers by quiz id and question id",
        description = "Returns the list of answers for the quiz and question with the provided id"
    )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No answers for the quiz with the porvided id")
        })

       @GetMapping("/quizzes/{quizId}/answers")
       public ResponseEntity<?> getAnswersByQuizId(@PathVariable Long quizId) {
           if (!quizRepository.existsById(quizId)) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("Quiz with the provided id does not exist");
           }
           List<Answer> answers = answerRepository.findByQuestionQuizId(quizId);
   
           if (answers.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
           }

           return ResponseEntity.ok(answers);
        
       }

       // Exercise 15 REST API endpoint for getting all categories

       @Operation(
        summary = "Get all categories of the quizzes",
        description = "Returns the list of categories for the quizzes"
    )

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

    @Operation(
        summary = "Get the category by id",
        description = "Returns the category with the provided id"
    )

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        QuizCategory category = quizCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with the provided id does not exist"));
        return ResponseEntity.ok(category);
    }


    // Exercise 17 REST API endpoint for getting the published quizzes by category id

    @Operation(
        summary = "Get the list of quizzes of a category",
        description = "Returns the list of quizzes of a category"
    )

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
