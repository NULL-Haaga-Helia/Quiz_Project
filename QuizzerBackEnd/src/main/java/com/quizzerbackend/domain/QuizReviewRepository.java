package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizReviewRepository extends JpaRepository<Reviews, Long> {
    
    List<Reviews> findByNickname(String nickname);
    List<Reviews> findByRating(int rating);
    List<Reviews> findByReview(String review);
    
    List<Reviews> findByQuizId(Long quizId);

    
}
