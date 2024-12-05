package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository  extends JpaRepository<Quiz, Long>{

    List<Quiz> findByName(String name);
    List<Quiz> findByIsPublished(boolean isPublished);
    List<Quiz> findByQuizCategoryId(Long quizCategoryId);
    List<Quiz> findByQuizCategoryIdAndIsPublished(Long quizCategoryId, boolean isPublished);
}
