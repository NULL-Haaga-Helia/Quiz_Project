package com.quizzerbackend.domain;

public class QuizReviewDTO {
    private String nickname;
    private int rating;
    private String review;
    private String writtenOn;

    public QuizReviewDTO() {
    }

    public QuizReviewDTO(Reviews reviews) {
        this.nickname = reviews.getNickname();
        this.rating = reviews.getRating();
        this.review = reviews.getReview();
        this.writtenOn = reviews.getWrittenOn();
    }

    public QuizReviewDTO(String nickname, int rating, String review, String writtenOn) {
        this.nickname = nickname;
        this.rating = rating;
        this.review = review;
        this.writtenOn = writtenOn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setRatingUser(String nickname) {
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

    public String getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(String writtenOn) {
        this.writtenOn = writtenOn;
    }

    @Override
    public String toString() {
        return "QuizRatingDTO [nickname=" + nickname + ", rating=" + rating + ", review=" + review + ", writtenOn="
                + writtenOn + "]";
    }

}
