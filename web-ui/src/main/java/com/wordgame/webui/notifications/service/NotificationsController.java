package com.wordgame.webui.notifications.service;

import com.wordgame.webui.configuration.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationsService;

    @PostMapping
    @ResponseBody
    public void createNotification(HttpServletRequest httpServletRequest, @RequestBody CreateNotificationRequest request) {
        if (!httpServletRequest.getHeader("X-Admin").equals(Password.PASSWORD)) {
            throw new RuntimeException("Unauthorized.");
        }

        notificationsService.createNotification(request);
    }

    @GetMapping
    @ResponseBody
    public NotificationsResponse getNotifications(HttpServletRequest request) {
        if (!request.getHeader("X-Admin").equals(Password.PASSWORD)) {
            throw new RuntimeException("Unauthorized.");
        }

        return notificationsService.getNotifications();
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(HttpServletRequest request, @PathVariable Integer id) {
        if (!request.getHeader("X-Admin").equals(Password.PASSWORD)) {
            throw new RuntimeException("Unauthorized.");
        }

        notificationsService.deleteNotification(id);
    }
}
