package com.quizzerbackend.domain;

public class AnswerDTO {
    private Long userAnswerId; 
    private Long answerId; 
    private Long questionId; 

  
    public AnswerDTO() {}

    public AnswerDTO(UserAnswer userAnswer) {
        this.userAnswerId = userAnswer.getUserAnswerId(); 
        this.answerId = userAnswer.getAnswer().getAnswerId();
        this.questionId = userAnswer.getQuestion().getQuestionId();  
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
