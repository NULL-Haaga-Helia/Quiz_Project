package com.quizzerbackend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizRepository;
import java.util.List;
;


//NOTE: THIS IS FOR FRONT END PREP.
//DO NOT MAKE CHANGES YET. 

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;

    //REST method to list all quizzes:
    // @RequestMapping(value="/quizzes", method = RequestMethod.GET)
    // public @ResponseBody List<Quiz> quizListRest() {	
    //     return quizRepository.findAll();    /*CHANGE TO FIND ONLY PUBLISHED */
    // } 
   
//The method above should work for now.

//Doesn't give an error:
/* 
@RequestMapping(value="/quizzes", method = RequestMethod.GET)
public @ResponseBody List<Quiz> quizListRest() {	
    return StreamSupport.stream(quizRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
}
*/

@RequestMapping(value="/quizzes", method = RequestMethod.GET)
public @ResponseBody List<Quiz> quizListRest() {
    return quizRepository.findByIsPublished(true);
}
}
