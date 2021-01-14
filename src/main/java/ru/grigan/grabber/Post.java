package ru.grigan.grabber;

import java.util.Date;

public class Post {
    private String title;
    private String userName;
    private String description;
    private Date dateCreation;

    public Post() {
    }

    public Post(String title, String userName, String description, Date dateCreation) {
        this.title = title;
        this.userName = userName;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "Post{" + "title='" + title
                + '\'' + ", userName='" + userName
                + '\'' + ", description='" + description
                + '\'' + ", dateCreation=" + dateCreation + '}';
    }
}
