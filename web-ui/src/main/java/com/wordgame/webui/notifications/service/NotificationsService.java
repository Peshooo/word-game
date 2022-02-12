package com.wordgame.webui.notifications.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationsService {
    private List<Notification> notifications = new ArrayList<>();
    private Integer idAutoIncrement = 0;

    public synchronized void createNotification(CreateNotificationRequest request) {
        ++idAutoIncrement;
        notifications.add(new Notification(idAutoIncrement, request.getMessage()));
    }

    public NotificationsResponse getNotifications() {
        return new NotificationsResponse(notifications);
    }

    public synchronized void deleteNotification(Integer id) {
        notifications.removeIf(notification -> notification.getId().equals(id));
    }
}
