package com.wordgame.webui.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationsService;

    @PostMapping
    @ResponseBody
    public void createNotification(@RequestBody CreateNotificationRequest request) {
        notificationsService.createNotification(request);
    }

    @GetMapping
    @ResponseBody
    public NotificationsResponse getNotifications() {
        return notificationsService.getNotifications();
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Integer id) {
        notificationsService.deleteNotification(id);
    }
}
