package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByQuestionDescription (String questionDescription);
    
}
