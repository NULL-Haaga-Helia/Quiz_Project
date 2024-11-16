package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuestionText(String questionText);
    List<Question> findByDifficulty(String difficulty);
    
    List<Question> findByQuizId(Long quizId);  
    
}
