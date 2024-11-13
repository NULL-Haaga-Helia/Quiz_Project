package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository  extends JpaRepository<Quiz, Long>{

    List<Quiz> findByName(String name);


}
