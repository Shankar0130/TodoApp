package com.shankaryadav.www.todoapp;

public class Nodes {

    public String title;
    public String description;
    public String id;

    public Nodes(){

    }

    public Nodes(String title, String description, String id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
