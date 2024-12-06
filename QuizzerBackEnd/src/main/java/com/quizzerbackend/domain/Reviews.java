package com.quizzerbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @NotBlank(message = "Quiz review nickname cannot be empty")
    private String nickname;

    @NotNull(message = "Rating must not be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")    
    private int rating;

    @NotBlank(message = "Quiz review cannot be empty")
    private String review;

    @NotBlank(message = "Quiz write cannot be empty")
    private String writtenOn;



    @ManyToOne
    @JoinColumn(name = "quizId")
    private Quiz quiz;

    public Reviews(){
        super();
    }

    public Reviews(Quiz quiz, String nickname, int rating, String review, String writtenOn){
        super();
        this.quiz=quiz;
        this.nickname = nickname;
        this.rating = rating;
        this.review = review;
        this.writtenOn = writtenOn;

    }

    public Reviews(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(String writtenOn) {
        this.writtenOn = writtenOn;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    
    @Override
    public String toString() {
        return "QuizRating [reviewId=" + reviewId + ", quiz=" + quiz.getName() + ", nickname=" + nickname
                + ", rating="
                + rating + ", review=" + review + ", writtenOn=" + writtenOn + "]";
    }
}
