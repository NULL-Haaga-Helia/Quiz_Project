package com.quizzerbackend.domain;

public class AnswerDTO {
    private Long answerId; 
    private Long questionId; 

    public AnswerDTO(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.questionId = answer.getQuestion().getQuestionId();  
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
