package kz.dostyk.ozindidamyt.ui.podcast.models;

import java.io.Serializable;
import java.util.Comparator;

public class Podcast implements Serializable {
    private String p_key;
    private String photo;
    private String title;
    private String topic;
    private String duration;
    private String link;
    private String language;

    public Podcast() {}

    public Podcast(String p_key, String title, String topic, String photo, String duration, String link, String language) {
        this.p_key = p_key;
        this.title = title;
        this.topic = topic;
        this.photo = photo;
        this.duration = duration;
        this.link = link;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getP_key() {
        return p_key;
    }

    public void setP_key(String p_key) {
        this.p_key = p_key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
