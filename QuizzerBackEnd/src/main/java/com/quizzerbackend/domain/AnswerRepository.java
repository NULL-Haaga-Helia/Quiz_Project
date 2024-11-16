package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByText(String text);
    List<Answer> findByQuestionQuestionId(Long questionId); 
    
}


