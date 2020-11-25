package kz.dostyk.ozindidamyt.ui.cinema.models;

import java.io.Serializable;
import java.util.Comparator;

public class Cinema implements Serializable {
    private String c_key;
    private String name;
    private Object photo;
    private String link;
    private String language;

    public Cinema() {}

    public Cinema(String c_key, String name, Object photo, String link, String language) {
        this.c_key = c_key;
        this.name = name;
        this.photo = photo;
        this.link = link;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getC_key() {
        return c_key;
    }

    public void setC_key(String c_key) {
        this.c_key = c_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
