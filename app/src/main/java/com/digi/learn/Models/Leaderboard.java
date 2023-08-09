package com.digi.learn.Models;

public class Leaderboard {
    private String position;
    private String img_url;
    private String points;
    private String username;

    public Leaderboard(String position, String username, String img_url, String points) {
        this.position = position;
        this.username = username;
        this.img_url = img_url;
        this.points = points;
    }

    public Leaderboard() {
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
