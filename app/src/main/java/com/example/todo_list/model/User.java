package com.example.todo_list.model;

public class User {
    private String username;
    private String Email;

    private String password;


    public User(String username,String Email, String password) {
        this.Email=Email;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }



    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


}
