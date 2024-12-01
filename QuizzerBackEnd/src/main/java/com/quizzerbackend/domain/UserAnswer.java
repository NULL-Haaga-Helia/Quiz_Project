package com.quizzerbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAnswerId; 

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer; 

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; 

    public UserAnswer() {

    }

    public UserAnswer(Answer answer, Question question) {
        this.answer = answer;
        this.question = question;
    }

    public Long getUserAnswerId() {
        return userAnswerId;
    }
    
    public void setUserAnswerId(Long userAnswerId) {
        this.userAnswerId = userAnswerId;
    }
    
    public Answer getAnswer() {
        return answer;
    }
    
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
}
