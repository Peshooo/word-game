package com.wordgame.webui.notifications.service;

public class CreateNotificationRequest {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CreateNotificationRequest(String message) {
        this.message = message;
    }

    public CreateNotificationRequest() {
    }
}
