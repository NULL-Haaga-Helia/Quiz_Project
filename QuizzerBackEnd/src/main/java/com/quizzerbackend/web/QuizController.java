package com.quizzerbackend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quizzerbackend.domain.Answer;
import com.quizzerbackend.domain.AnswerRepository;
import com.quizzerbackend.domain.Question;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizRepository;

@Controller
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    //QUIZ METHODS//
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
        
        return "addquiz";
    }

    // Save a quiz
    @RequestMapping(value = "/savequiz", method = RequestMethod.POST)
    public String save(Quiz quiz) {
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
    	return "editquiz";
    }   
    
    //QUESTION METHODS//
    // List all questions (to a specific quiz)
    @RequestMapping("/questionlist/{id}")
    public String showQuestionsForQuiz(@PathVariable("id") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));
        
        List<Question> questions = questionRepository.findByQuizId(quizId);
        
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        
       
        return "questionlist"; 
    }
    
   // Add a question (to a specific quiz)
    @RequestMapping(value = "/addquestion/{quizId}", method = RequestMethod.GET)
    public String addQuestion(@PathVariable("quizId") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", new Question());

        return "addquestion";
    }
 

    // Save a question (after adding it)
    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String save(Question question, @RequestParam("quizId") Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        question.setQuiz(quiz);

        questionRepository.save(question);

        return "redirect:/questionlist/" + quizId;
    }

 
    //Delete a question
    @RequestMapping(value = "/deletequestion/{id}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("id") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));
        Long quizId = question.getQuiz().getId(); 

        questionRepository.deleteById(questionId);

        return "redirect:/questionlist/" + quizId;
    }

    //Edit a question
    @RequestMapping(value = "/editquestion/{quizId}/{questionId}", method = RequestMethod.GET)
    public String editQuestion(@PathVariable("quizId") Long quizId, @PathVariable("questionId") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));
           
        Quiz quiz = question.getQuiz();
        
        model.addAttribute("quiz", quiz);
        model.addAttribute("question", question);

        return "editquestion";
    }

    
    // Save edited question:
    @RequestMapping(value = "/saveeditedquestion", method = RequestMethod.POST)
    public String saveEditedQuestion(Question question, @RequestParam("quizId") Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        question.setQuiz(quiz);

        questionRepository.save(question);

        return "redirect:/questionlist/" + quizId;  
    }

    @RequestMapping(value = "/answerlist/{questionId}", method = RequestMethod.GET)
    public String viewAnswerOptions(@PathVariable("questionId") Long questionId, Model model) {
    Question question = questionRepository.findById(questionId).orElse(null);
    List<Answer> answerOptions = answerRepository.findByQuestionQuestionId(questionId);
    model.addAttribute("question", question);
    model.addAttribute("answerOptions", answerOptions);
    return "answerlist";


    //ANSWER METHODS//
    //>>TBA
   
}}
