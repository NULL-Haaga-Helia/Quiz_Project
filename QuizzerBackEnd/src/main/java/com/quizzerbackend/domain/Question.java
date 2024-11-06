package com.quizzerbackend.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String questionDescription;

    public Question() {

    }

    public Question(String questionDescription) {
        super();
        this.questionDescription = questionDescription;
    }

    public String getQuestionDescreption() {
        return questionDescription;
    }

    public void setQuestionDescreption(String questionDescription) {
        this.questionDescription = questionDescription;
    }
}