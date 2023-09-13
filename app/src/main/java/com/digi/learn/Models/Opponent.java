package com.digi.learn.Models;

public class Opponent {

    private String firstname;
    private String lastname;
    private String level;
    private String profile_image;
    private String xp;


    public Opponent() {
    }

    public Opponent(String firstname, String lastname, String level, String profile_image, String xp) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.level = level;
        this.profile_image = profile_image;
        this.xp = xp;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }
}
