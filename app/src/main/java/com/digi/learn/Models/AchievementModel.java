package com.digi.learn.Models;

import android.graphics.drawable.Drawable;

public class AchievementModel {
    private String achievementName;
    private String achievementDescription;
    private String achievementImageLink;
    private int drawableImage;

    public AchievementModel() {
    }

    public AchievementModel(String achievementName, String achievementDescription, int drawableImage) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.drawableImage = drawableImage;
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public AchievementModel(String achievementName, String achievementDescription, String achievementImageLink) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.achievementImageLink = achievementImageLink;
    }


    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }

    public String getAchievementImageLink() {
        return achievementImageLink;
    }

    public void setAchievementImageLink(String achievementImageLink) {
        this.achievementImageLink = achievementImageLink;
    }
}
