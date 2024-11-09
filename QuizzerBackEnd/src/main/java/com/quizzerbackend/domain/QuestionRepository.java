package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.quizzerbackend.domain.Quiz;
import com.quizzerbackend.domain.QuizRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByQuestionText(String questionText);
    List<Question> findByDifficulty(String difficulty);
    
    List<Question> findByQuizId(Long quizId);  
    
}
