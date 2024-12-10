package com.quizzerbackend.web;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quizzerbackend.domain.Answer;
import com.quizzerbackend.domain.AnswerDTO;
import com.quizzerbackend.domain.AnswerRepository;
import com.quizzerbackend.domain.Question;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.QuizReviewRepository;
import com.quizzerbackend.domain.Reviews;
import com.quizzerbackend.domain.QuizCategory;
import com.quizzerbackend.domain.QuizCategoryRepository;

import jakarta.validation.Valid;

@Controller
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizCategoryRepository quizCategoryRepository;

      @Autowired
    private QuizReviewRepository quizReviewRepository;

    // QUIZ METHODS//
    // List all quizzes
    @RequestMapping(value = { "/", "/quizlist" })
    public String quizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }

    // Add a quiz
    @RequestMapping(value = "/addquiz")
    public String addQuiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("quizCategories", quizCategoryRepository.findAll());

        return "addquiz";
    }

    // Save a quiz
    @RequestMapping(value = "/savequiz", method = RequestMethod.POST)
    public String save(@Valid Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (quiz.getId() != null) {
                return "editquiz";
            } else {
                return "addquiz";
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDate = LocalDate.now().format(formatter);
        quiz.setAddedOn(currentDate);

        quizRepository.save(quiz);

        return "redirect:/quizlist";
    }

    // Delete a quiz
    @RequestMapping(value = "/deletequiz/{id}", method = RequestMethod.GET)

    public String deleteQuiz(@PathVariable("id") Long id, Model model) {
        quizRepository.deleteById(id);
        return "redirect:../quizlist";

    }

    // Edit a quiz
    @RequestMapping(value = "/editquiz/{id}", method = RequestMethod.GET)
    public String editQuiz(@PathVariable("id") Long quizId, Model model) {
        model.addAttribute("quiz", quizRepository.findById(quizId));
        model.addAttribute("quizCategories", quizCategoryRepository.findAll());
        return "editquiz";
    }

    // QUESTION METHODS//
    // List all questions (for a specific quiz)
    @RequestMapping("/questionlist/{id}")
    public String showQuestionsForQuiz(@PathVariable("id") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        List<Question> questions = questionRepository.findByQuizId(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "questionlist";
    }

    // Add a question
    @RequestMapping(value = "/addquestion/{quizId}", method = RequestMethod.GET)
    public String addQuestion(@PathVariable("quizId") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", new Question(quiz));

        return "addquestion";
    }

    // Save a question
    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String saveAddedQuestion(@Valid Question question, BindingResult bindingResult, Model model) {
        Quiz quiz = question.getQuiz();
        model.addAttribute("quiz", quiz);

        if (bindingResult.hasErrors()) {
            if (question.getQuestionId() != null) {
                return "editquestion";
            } else {
                return "addquestion";
            }
        }
        questionRepository.save(question);

        return "redirect:/questionlist/" + quiz.getId();
    }

    // Delete a question
    @RequestMapping(value = "/deletequestion/{id}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("id") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));
        Long quizId = question.getQuiz().getId();

        questionRepository.deleteById(questionId);

        return "redirect:/questionlist/" + quizId;
    }

    // Edit a question
    @RequestMapping(value = "/editquestion/{quizId}/{questionId}", method = RequestMethod.GET)
    public String editQuestion(@PathVariable("quizId") Long quizId, @PathVariable("questionId") Long questionId,
            Model model) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        Quiz quiz = question.getQuiz();

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", question);

        return "editquestion";
    }

    // ANSWER METHODS//
    // List all answers (for a specific question)
    @RequestMapping(value = "/answerlist/{questionId}", method = RequestMethod.GET)
    public String viewAnswerOptions(@PathVariable("questionId") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId).orElse(null);
        List<Answer> answerOptions = answerRepository.findByQuestionQuestionId(questionId);
        model.addAttribute("question", question);
        model.addAttribute("answerOptions", answerOptions);
        return "answerlist";
    }

    // Add an answer
    @RequestMapping(value = "/addanswer/{questionId}", method = RequestMethod.GET)
    public String addAnswer(@PathVariable("questionId") Long questionId, Model model) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        model.addAttribute("question", question);
        model.addAttribute("answer", new Answer(question));

        return "addanswer";
    }

    // Save answer
    @RequestMapping(value = "/saveanswer", method = RequestMethod.POST)
    public String saveAnswer(@Valid Answer answer, BindingResult bindingResult, Model model) {
        Question question = answer.getQuestion();
        model.addAttribute("question", question);

        if (bindingResult.hasErrors()) {

            if (answer.getAnswerId() != null) {
                return "editanswer";
            } else {
                return "addanswer";
            }
        }

        answerRepository.save(answer);
        return "redirect:/answerlist/" + question.getQuestionId();
    }

    // Delete an answer
    @RequestMapping(value = "/deleteanswer/{id}", method = RequestMethod.GET)
    public String deleteAnswer(@PathVariable("id") Long answerId, Model model) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid answer ID"));
        Long questionId = answer.getQuestion().getQuestionId();

        answerRepository.deleteById(answerId);

        return "redirect:/answerlist/" + questionId;
    }

    // Edit an answer
    @RequestMapping(value = "/editanswer/{questionId}/{answerId}", method = RequestMethod.GET)
    public String editAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId,
            Model model) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid answer ID"));

        Question question = answer.getQuestion();

        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        return "editanswer";
    }

    // QUIZ CATEGORY METHODS//
    // List all quiz categories
    @RequestMapping(value = "/quizcategorylist")
    public String quizCategoryList(Model model) {
        model.addAttribute("quizCategories", quizCategoryRepository.findAll());
        return "quizcategorylist";
    }

    // Add a quiz category
    @RequestMapping(value = "/addquizcategory")
    public String addQuizCategory(Model model) {
        model.addAttribute("quizCategory", new QuizCategory());
        return "addquizcategory";
    }

    // Delete a quiz category
    @RequestMapping(value = "/deletequizcategory/{id}", method = RequestMethod.GET)
    public String deleteQuizCategory(@PathVariable("id") Long id, Model model) {
        quizCategoryRepository.deleteById(id);
        return "redirect:../quizcategorylist";
    }

    // Edit a quiz category
    @RequestMapping(value = "/editquizcategory/{id}", method = RequestMethod.GET)
    public String editQuizCategory(@PathVariable("id") Long quizCategoryId, Model model) {
        model.addAttribute("quizCategory", quizCategoryRepository.findById(quizCategoryId));
        return "editquizcategory";
    }

    // Save a quiz category
    @RequestMapping(value = "/savequizcategory", method = RequestMethod.POST)
    public String saveQuizCategory(@Valid QuizCategory quizCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (quizCategory.getId() != null) {
                return "editquizcategory";
            } else {
                return "addquizcategory";
            }
        }

        quizCategoryRepository.save(quizCategory);

        return "redirect:/quizcategorylist";
    }

    
    //Review list
    @GetMapping("/{quizId}/reviews")
    public ResponseEntity<List<Reviews>> getReviewsByQuizId(@PathVariable("quizId") Long quizId) {
        List<Reviews> reviews = quizReviewRepository.findByQuizId(quizId);
        return ResponseEntity.ok(reviews);
    }



    //Add Review
    @RequestMapping(value = "/addreview")
    public String addQuizReview(Model model) {
        model.addAttribute("quizReview", new Reviews());
        return "addreview";
    }

    // Delete a quiz review
    @RequestMapping(value = "/deletereview/{id}", method = RequestMethod.DELETE)
public ResponseEntity<?> deleteQuizReview(@PathVariable("id") Long id) {
    if (!quizReviewRepository.existsById(id)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
    }
    quizReviewRepository.deleteById(id);
    return ResponseEntity.ok().body("Review deleted successfully");
}
   
     // Edit a quiz review
     @RequestMapping(value = "/editreview/{id}", method = RequestMethod.PUT)
     public ResponseEntity<?> editQuizReview(@PathVariable("id") Long reviewId, @RequestBody Reviews updatedReview) {
         Optional<Reviews> existingReview = quizReviewRepository.findById(reviewId);
         if (!existingReview.isPresent()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
         }
     
         Reviews review = existingReview.get();
         review.setRating(updatedReview.getRating());
         review.setReview(updatedReview.getReview());
         quizReviewRepository.save(review);
     
         return ResponseEntity.ok(review); // Return the updated review as JSON
     }
 
}
