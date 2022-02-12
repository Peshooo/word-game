package com.wordgame.webui.notifications.service;

import java.time.LocalDate;

public class Notification {
    private Integer id;
    private String message;

    public Notification(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Notification() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
