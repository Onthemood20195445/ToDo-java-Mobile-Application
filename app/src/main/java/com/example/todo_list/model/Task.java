package com.example.todo_list.model;

public class Task {
    String title;
    String Username;
    String status;

    public Task(String title, String username, String status) {
        this.title = title;
        this.Username = username;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
