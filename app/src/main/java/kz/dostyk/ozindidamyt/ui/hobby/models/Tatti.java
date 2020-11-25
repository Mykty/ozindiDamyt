package kz.dostyk.ozindidamyt.ui.hobby.models;

import java.io.Serializable;

public class Tatti implements Serializable {
    private String t_key;
    private String title;
    private String duration;
    private String link;
    private int photo;

    public Tatti() {}

    public Tatti(String t_key, String title, String duration, String link, int photo) {
        this.t_key = t_key;
        this.title = title;
        this.duration = duration;
        this.link = link;
        this.photo = photo;
    }

    public String getDuration() {
        return duration;
    }

    public String getT_key() {
        return t_key;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public int getPhoto() {
        return photo;
    }
}
