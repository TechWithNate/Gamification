package com.digi.learn.Models;

public class Post {

    private String uID;
    private String name;
    private String title;
    private String date;
    private String postUrl;
    private String post;
    private String upvote;
    private String comment;
    private String profilePicture;

    public Post() {
    }

    public Post(String name, String title, String date, String postUrl, String post, String upvote, String comment) {
        this.name = name;
        this.title = title;
        this.date = date;
        this.postUrl = postUrl;
        this.post = post;
        this.upvote = upvote;
        this.comment = comment;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setName(String name) {
        this.name = name;
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
