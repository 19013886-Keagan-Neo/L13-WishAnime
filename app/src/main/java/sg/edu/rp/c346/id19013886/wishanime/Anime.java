package sg.edu.rp.c346.id19013886.wishanime;

import java.io.Serializable;

public class Anime implements Serializable {

    private int id;
    private String title;
    private String description;
    private int season;
    private int stars;

    public Anime(String title, String description, int season, int stars) {
        this.title = title;
        this.description = description;
        this.season = season;
        this.stars = stars;
    }

    public Anime(int id, String title, String description, int season, int stars) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.season = season;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Anime setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Anime setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Anime setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getSeason() {
        return season;
    }

    public Anime setSeason(int season) {
        this.season = season;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Anime setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @Override
    public String toString() {
        String starsString = "";
        if (stars == 5) {
            starsString = "*****";
        } else if (stars == 4) {
            starsString = "****";
        } else if (stars == 3) {
            starsString = "***";
        } else if (stars == 2) {
            starsString = "**";
        } else {
            starsString = "*";
        }

        return starsString;
    }

}
