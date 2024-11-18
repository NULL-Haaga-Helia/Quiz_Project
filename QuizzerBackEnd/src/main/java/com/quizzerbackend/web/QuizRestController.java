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



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;


@RequestMapping(value="/quizzes", method = RequestMethod.GET)
public @ResponseBody List<Quiz> quizListRest() {
    return quizRepository.findByIsPublished(true);
}
}
