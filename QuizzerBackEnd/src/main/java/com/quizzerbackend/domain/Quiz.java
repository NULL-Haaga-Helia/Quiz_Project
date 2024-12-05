package com.quizzerbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Quiz name cannot be empty")
    private String name;

    @NotBlank(message = "Quiz description cannot be empty")
    private String description;

    private String addedOn;
    private boolean isPublished;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    @JsonManagedReference
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private QuizCategory quizCategory;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<QuizRating> quizRatings;

    public Quiz() {
    }

    public Quiz(String name, String description, String addedOn, boolean isPublished, QuizCategory quizCategory) {
        super();
        this.name = name;
        this.description = description;
        this.addedOn = addedOn;
        this.isPublished = isPublished;
        this.quizCategory = quizCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public QuizCategory getQuizCategory() {
        return quizCategory;
    }

    public void setQuizCategory(QuizCategory quizCategory) {
        this.quizCategory = quizCategory;
    }

    public List<QuizRating> getQuizRatings() {
        return quizRatings;
    }

    public void setQuizRatings(List<QuizRating> quizRatings) {
        this.quizRatings = quizRatings;
    }

    @Override
    public String toString() {

        if (this.quizCategory != null) {
            return "Quiz [id=" + id + ", name=" + name + ", description=" + description + ", addedOn=" + addedOn
                    + ", isPublished=" + isPublished + ", quizCategory=" + quizCategory.getName() + "]";
        } else {
            return "Quiz [id=" + id + ", name=" + name + ", description=" + description + ", addedOn=" + addedOn
                    + ", isPublished=" + isPublished + "]";
        }
    }

}
