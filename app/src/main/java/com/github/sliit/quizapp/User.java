package com.github.sliit.quizapp;

public class User
{

    private int uid;
    private String username;
    private String email;
    private String password;
    private int highscore;     // Highscore of each player


    public User(String username, String email, String password, int highscore) // Constructor
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.highscore = highscore;
    }


/* ----------- Getters and Setters --------------*/

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }


}