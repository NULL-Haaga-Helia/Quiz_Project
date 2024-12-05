package com.quizzerbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizReviewRepository extends JpaRepository<QuizRating, Long> {
    
    List<QuizRating> findByRatingUser(String ratingUser);
    List<QuizRating> findByRating(int rating);
    List<QuizRating> findByReview(String review);
    
    List<QuizRating> findByQuizId(Long quizId);

    
}
