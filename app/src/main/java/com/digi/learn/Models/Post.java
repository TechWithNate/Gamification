package com.digi.learn.Models;

public class Post {

    private String uID;
    User user;
    private String title;
    private String date;
    private String postUrl;
    private String post;
    private String upvote;
    private String comment;

    public Post() {
    }

    public Post(User user, String title, String date, String postUrl, String post, String upvote, String comment) {
        this.user = user;
        this.title = title;
        this.date = date;
        this.postUrl = postUrl;
        this.post = post;
        this.upvote = upvote;
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUpvote() {
        return upvote;
    }

    public void setUpvote(String upvote) {
        this.upvote = upvote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
