package com.quizzerbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class QuizRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ratingId;
    
    private String ratingUser;
    private int rating;
    private String review;
    private String writtenOn;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id")
    private Quiz quiz;

    public QuizRating() {
        super();
    }

    public QuizRating(Quiz quiz, String ratingUser, int rating, String review, String writtenOn) {
        super();
        this.quiz = quiz;
        this.ratingUser = ratingUser;
        this.rating = rating;
        this.review = review;
        this.writtenOn = writtenOn;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(String ratingUser) {
        this.ratingUser = ratingUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(String writtenOn) {
        this.writtenOn = writtenOn;
    }

    @Override
    public String toString() {
        return "QuizRating [ratingId=" + ratingId + ", quiz=" + quiz.getName() + ", ratingUser=" + ratingUser
                + ", rating="
                + rating + ", review=" + review + ", writtenOn=" + writtenOn + "]";
    }

}
