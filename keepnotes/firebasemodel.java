package com.example.keepnotes;

public class firebasemodel {
    private String title;
    private String content;

    // Default constructor (required for Firebase)
    public firebasemodel() {

    }

    // Parameterized constructor to initialize title and content
    public firebasemodel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter method for title
    public String getTitle() {
        return title;
    }

    // Setter method for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method for content
    public String getContent() {
        return content;
    }

    // Setter method for content
    public void setContent(String content) {
        this.content = content;
    }
}
