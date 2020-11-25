package kz.dostyk.ozindidamyt.ui.hobby.models;

import java.io.Serializable;

public class Hobby implements Serializable {
    private String h_key;
    private String title;
    private String desc;
    private String link;
    private Object photo;

    public Hobby() {}

    public Hobby(String h_key, String title, String desc, String link, Object photo) {
        this.h_key = h_key;
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.photo = photo;
    }

    public String getH_key() {
        return h_key;
    }

    public void setH_key(String h_key) {
        this.h_key = h_key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Object getPhoto() {
        return photo;
    }
}
