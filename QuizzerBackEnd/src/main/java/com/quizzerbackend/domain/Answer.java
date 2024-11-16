package com.quizzerbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long answerId;

    private boolean isCorrect;

    @NotBlank(message = "Answer text cannot be empty")
    private String text;

   
    @ManyToOne
    @JoinColumn(name = "questionId")  
    private Question question;


    public Answer() {}

    public Answer(Question question) {
        super();
        this.question = question;
    }

    public Answer(boolean isCorrect, String text, Question question) {
        super();
        this.isCorrect= isCorrect;
        this.text = text;
        this.question = question;
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

