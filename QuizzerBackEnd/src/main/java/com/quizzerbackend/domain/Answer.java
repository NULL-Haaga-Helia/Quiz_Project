package com.quizzerbackend.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
    @JsonBackReference
    private Question question;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    @JsonIgnore
    private List<UserAnswer> UserAnswers;

    public Answer() {
    }

    public Answer(Question question) {
        super();
        this.question = question;
    }

    public Answer(boolean isCorrect, String text, Question question) {
        super();
        this.isCorrect = isCorrect;
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

    public List<UserAnswer> getUserAnswers() {
        return UserAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        UserAnswers = userAnswers;
    }

}
