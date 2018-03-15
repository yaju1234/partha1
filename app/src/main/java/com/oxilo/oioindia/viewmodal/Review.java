package com.oxilo.oioindia.viewmodal;

/**
 * Created by yaju on 15/3/18.
 */

public class Review {

    private String review;
    private String rating;
    private String image;
    private String user_id;
    private String username;


    public Review(String review, String rating, String image, String user_id, String username) {
        this.review = review;
        this.rating = rating;
        this.image = image;
        this.user_id = user_id;
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
