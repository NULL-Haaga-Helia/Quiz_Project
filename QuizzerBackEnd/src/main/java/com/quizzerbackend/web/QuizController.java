package com.quizzerbackend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quizzerbackend.domain.QuizRepository;

import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.Question;

@Controller
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;


    //QUIZ METHODS//
    // List all quizzes
    @RequestMapping(value = { "/", "/quizlist" })
    public String quizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }

    // Add a quiz 0_0
    @RequestMapping(value = "/addquiz")
    public String addQuiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        
        return "addquiz";
    }

    // Save a quiz :D
    @RequestMapping(value = "/savequiz", method = RequestMethod.POST)
    public String save(Quiz quiz) {
        quizRepository.save(quiz);
        
        return "redirect:/quizlist";
    }

    // Delete a quiz ;P
    @RequestMapping(value = "/deletequiz/{id}", method = RequestMethod.GET)
   
    public String deleteQuiz(@PathVariable("id") Long id, Model model) {
        quizRepository.deleteById(id);
        return "redirect:../quizlist";

    }

    //Edit a quiz    <<TBA
    
    //QUESTION METHODS//
    // List all questions
    
    @RequestMapping(value = { "/questionlist" })
    public String questionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "questionlist";
    }
       
    //NOTE  -> KEEP FOR LISTING ALL QUESTIONS RELATED TO A SPECIFIC QUIZ:
    /* 
    @RequestMapping(value = "/questionlist/{id}")
    public String questionList(@PathVariable("id") Long id, Model model) {
        model.addAttribute("quiz", quizRepository.findById(id));
        model.addAttribute("questions", questionRepository.findAll());
        return "questionlist";
    }
    */


    //Add a question
    @RequestMapping(value = "/addquestion")
    public String addQuestion(Model model) {
        model.addAttribute("question", new Question());
       
        return "addquestion";
    }
    
    //Save a question
    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String save(Question question) {
        questionRepository.save(question);
        return "redirect:questionlist";
    }

    //Delete a question
    @RequestMapping(value = "/deletequestion/{id}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("id") Long questionId, Model model) {
    	questionRepository.deleteById(questionId);
        return "redirect:../questionlist";
    }     


    //Edit a question
    @RequestMapping(value = "/editquestion/{id}", method = RequestMethod.GET)
    public String editQuestion(@PathVariable("id") Long questionId, Model model) {
    	model.addAttribute("question", questionRepository.findById(questionId));
    	return "editquestion";
    }  

    //ANSWER METHODS//
    //>>TBA
   
}
