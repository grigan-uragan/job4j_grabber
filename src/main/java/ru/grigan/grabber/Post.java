package ru.grigan.grabber;

import java.util.Date;
import java.util.List;

public class Post {
    private String title;
    private String userName;
    private String description;
    private Date dateCreation;
    private List<Post> answersList;

    public Post() {
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

    public List<Post> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Post> answersList) {
        this.answersList = answersList;
    }
}
