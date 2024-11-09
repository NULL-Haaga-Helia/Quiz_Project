package com.quizzerbackend.domain;

import jakarta.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long answerId;
    private boolean isCorrect;
    private String text;

   
    @ManyToOne
    @JoinColumn(name = "question_id")  
    private Question question;


    public Answer() {}

    public Answer(boolean isCorrect, Question question, String text) {
        super();
        this.isCorrect= isCorrect;
        this.question = question;
        this.text = text;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }


    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

