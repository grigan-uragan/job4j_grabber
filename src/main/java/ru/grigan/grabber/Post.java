package ru.grigan.grabber;

import java.util.Date;

public class Post {
    private String url;
    private String title;
    private String username;
    private String description;
    private Date dateCreation;

    public Post() {
    }

    public Post(String url, String title, String username, String description, Date dateCreation) {
        this.url = url;
        this.title = title;
        this.username = username;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Post{" + "url='" + url + '\''
                + ", title='" + title + '\''
                + ", username='" + username + '\''
                + ", description='" + description + '\''
                + ", dateCreation=" + dateCreation + '}';
    }
}
