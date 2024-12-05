package com.quizzerbackend.domain;

public class QuizRatingDTO {
    private String ratingUser;
    private int rating;
    private String review;
    private String writtenOn;

    public QuizRatingDTO() {
    }

    public QuizRatingDTO(QuizRating quizRating) {
        this.ratingUser = quizRating.getRatingUser();
        this.rating = quizRating.getRating();
        this.review = quizRating.getReview();
        this.writtenOn = quizRating.getWrittenOn();
    }

    public QuizRatingDTO(String ratingUser, int rating, String review, String writtenOn) {
        this.ratingUser = ratingUser;
        this.rating = rating;
        this.review = review;
        this.writtenOn = writtenOn;
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
        return "QuizRatingDTO [ratingUser=" + ratingUser + ", rating=" + rating + ", review=" + review + ", writtenOn="
                + writtenOn + "]";
    }

}
