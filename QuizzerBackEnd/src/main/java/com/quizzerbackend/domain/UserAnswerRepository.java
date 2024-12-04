package com.quizzerbackend.domain;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByAnswerQuestionQuizId(Long quizId);
}
