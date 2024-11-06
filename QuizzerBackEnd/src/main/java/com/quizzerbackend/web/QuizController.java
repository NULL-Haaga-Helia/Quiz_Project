package com.quizzerbackend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quizzerbackend.domain.QuizRepository;
import com.quizzerbackend.domain.DifficultyRepository;
import com.quizzerbackend.domain.QuestionRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.Question;

@Controller
public class QuizController {

    @Autowired
    private QuizRepository quizrepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @RequestMapping(value = { "/", "/quizlist" })
    public String quizList(Model model) {
        model.addAttribute("quizzes", quizrepository.findAll());
        return "quizlist";
    }

    // add a quiz 0_0
    @RequestMapping(value = "/addquiz")
    public String addQuiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("difficulty", difficultyRepository.findAll());
        return "addquiz";
    }

    // save the quiz :D
    @RequestMapping(value = "/savequiz")
    public String save(Quiz quiz) {
        quizrepository.save(quiz);
        System.out.println(quiz);
        return "redirect:/quizlist";
    }

    // delete a quiz ;P
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    // @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteQuiz(@PathVariable("id") Long id, Model model) {
        quizrepository.deleteById(id);
        return "redirect:../quizlist";

    }

    @RequestMapping(value = { "/questionlist" })
    public String questions(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "questionlist";
    }

    @RequestMapping(value = "/addquestion")
    public String addQuestion(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("questions", questionRepository.findAll());
        return "addquestion";
    }

    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String save(Question question) {
        questionRepository.save(question);
        return "redirect:questionlist";
    }

    /*
     * @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
     * public String deleteQuestion(@PathVariable("id") Long id, Model model) {
     * questionRepository.deleteById(id)
     * return "redirect:../questionlist";
     * }
     */
}
