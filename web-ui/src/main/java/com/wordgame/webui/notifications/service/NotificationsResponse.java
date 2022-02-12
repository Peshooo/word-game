package com.wordgame.webui.notifications.service;

import java.util.List;

public class NotificationsResponse {
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public NotificationsResponse(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public NotificationsResponse() {
    }
}
