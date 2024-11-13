package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByQuestionText(String questionText);
    List<Question> findByDifficulty(String difficulty);
    
    List<Question> findByQuizId(Long quizId);  
    
}
