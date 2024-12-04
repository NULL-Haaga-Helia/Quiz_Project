package com.quizzerbackend.domain;

public class AnswerDTO {
    private Long userAnswerId; 
    private Long answerId;

  
    public AnswerDTO() {}

    public AnswerDTO(UserAnswer userAnswer) {
        this.userAnswerId = userAnswer.getUserAnswerId(); 
        this.answerId = userAnswer.getAnswer().getAnswerId();
    }

    public Long getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(Long userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
}
