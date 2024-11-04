package com.quizzerbackend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.Quiz;



    @Controller
public class QuizController {
    @Autowired
    private QuizRepository quizrepository;
    
    @RequestMapping(value= {"/", "/quizlist"})
    public String quizList(Model model) {	
        model.addAttribute("quizzes", quizrepository.findAll());
        return "quizlist";
    }
}

