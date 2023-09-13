package com.digi.learn.Models;

public class User {
    private String uID;
    private String firstname;
    private String lastname;
    private String studentLevel;
    private String studentId;
    private String email;
    private String profilePicture;
    private String userBio;

    private String linkedIn;
    private String git;
    private String tel;
    private String fb;

    private int points;
    private int gameLevel;
    private String selectedCourse;


    public User() {
    }

    public User(String uID, String firstname, String lastname, String studentLevel, String studentId, String email) {
        this.uID = uID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.studentLevel = studentLevel;
        this.studentId = studentId;
        this.email = email;
    }


    public User(String uID, String firstname, String lastname, String studentLevel, String studentId, String email, String profilePicture, String userBio, String linkedIn, String git, String tel, String fb, int points, int gameLevel) {
        this.uID = uID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.studentLevel = studentLevel;
        this.studentId = studentId;
        this.email = email;
        this.profilePicture = profilePicture;
        this.userBio = userBio;
        this.linkedIn = linkedIn;
        this.git = git;
        this.tel = tel;
        this.fb = fb;
        this.points = points;
        this.gameLevel = gameLevel;
    }

    public User(String uID, String firstname, String lastname, String studentLevel, String studentId, String email, String userBio, String linkedIn, String git, String tel, String fb) {
        this.uID = uID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.studentLevel = studentLevel;
        this.studentId = studentId;
        this.email = email;
        this.userBio = userBio;
        this.linkedIn = linkedIn;
        this.git = git;
        this.tel = tel;
        this.fb = fb;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserBio() {
        return userBio;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}
