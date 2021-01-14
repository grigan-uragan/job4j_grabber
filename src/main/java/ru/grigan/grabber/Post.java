package ru.grigan.grabber;

import java.util.Date;
import java.util.Objects;

public class Post {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Post{" + " id=" + id + ", url='" + url + '\''
                + ", title='" + title + '\''
                + ", username='" + username + '\''
                + ", description='" + description + '\''
                + ", dateCreation=" + dateCreation + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return  Objects.equals(url, post.url)
                && Objects.equals(title, post.title)
                && Objects.equals(username, post.username)
                && Objects.equals(description, post.description)
                && Objects.equals(dateCreation, post.dateCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, title, username, description, dateCreation);
    }
}
