package com.quizzerbackend.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    @NotBlank(message = "Question text cannot be empty")
    private String questionText;

    @NotBlank(message = "Difficulty must be selected")
    private String difficulty;

   
    @ManyToOne
    @JoinColumn(name = "id")  //Quiz's id field
    private Quiz quiz;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Answer> answers;


    public Question() {}

    public Question(String questionText, String difficulty, Quiz quiz) {
		super();
		this.questionText= questionText;
		this.difficulty = difficulty;
        this.quiz = quiz;
	}

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

   
    

    @Override
    public String toString() {
        if (this.quiz != null)
            return "Question [id=" + questionId + ", questionText=" + questionText + ", difficulty=" + difficulty + ", quiz=" + this.getQuiz() + "]";
        else
            return "Question [id=" + questionId + ", questionText=" + questionText + ", difficulty=" + difficulty + ", quiz=" + quiz + "]";

    }


}