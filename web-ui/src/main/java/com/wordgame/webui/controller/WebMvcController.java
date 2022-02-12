package com.wordgame.webui.controller;

import com.google.common.collect.ImmutableMap;
import com.wordgame.webui.model.GameRecord;
import com.wordgame.webui.model.GameRecordsResponse;
import com.wordgame.webui.notifications.service.Notification;
import com.wordgame.webui.notifications.service.NotificationsService;
import com.wordgame.webui.service.GameServerRestClient;
import com.wordgame.webui.service.RecordsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebMvcController {
    @Autowired
    private RecordsStorageService recordsStorageService;

    @Autowired
    private GameServerRestClient gameServerRestClient;

    @Autowired
    private NotificationsService notificationsService;

    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        List<String> notifications = notificationsService.getNotifications().getNotifications().stream().map(Notification::getMessage).collect(Collectors.collectingAndThen(Collectors.toList(), l -> {
            Collections.reverse(l);
            return l;
        }));
        String notificationsAll = notifications.stream().map(notification -> "\"" + notification + "\"").collect(Collectors.joining(","));
        GameRecordsResponse gameRecordsResponse = recordsStorageService.getGameRecords();
        List<GameRecord> standardTopFive = gameRecordsResponse.getGameRecordsByGameMode().get("standard"); //Ugly but it's kinda client-side code so at least some ugliness is required.
        List<GameRecord> survivalTopFive = gameRecordsResponse.getGameRecordsByGameMode().get("survival"); //Ugly but it's kinda client-side code so at least some ugliness is required.

        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("standardRecords", standardTopFive)
                        .put("survivalRecords", survivalTopFive)
                        .put("notificationsCount", notifications.size())// notificationsService.getNotifications().getMessages().size())
                        .put("notificationsAll", notificationsAll)//notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("index", context);
    }

    @RequestMapping("/standard")
    public ModelAndView standard(HttpServletRequest request, HttpServletResponse response) {
        List<String> notifications = notificationsService.getNotifications().getNotifications().stream().map(Notification::getMessage).collect(Collectors.collectingAndThen(Collectors.toList(), l -> {
            Collections.reverse(l);
            return l;
        }));
        String notificationsAll = notifications.stream().map(notification -> "\"" + notification + "\"").collect(Collectors.joining(","));
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "standard")
                        .put("gameServerRestClient", gameServerRestClient)
                        .put("notificationsCount", notifications.size())// notificationsService.getNotifications().getMessages().size())
                        .put("notificationsAll", notificationsAll)//notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/survival")
    public ModelAndView survival(HttpServletRequest request, HttpServletResponse response) {
        List<String> notifications = notificationsService.getNotifications().getNotifications().stream().map(Notification::getMessage).collect(Collectors.collectingAndThen(Collectors.toList(), l -> {
            Collections.reverse(l);
            return l;
        }));
        String notificationsAll = notifications.stream().map(notification -> "\"" + notification + "\"").collect(Collectors.joining(","));
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "survival")
                        .put("gameServerRestClient", gameServerRestClient)
                        .put("notificationsCount", notifications.size())// notificationsService.getNotifications().getMessages().size())
                        .put("notificationsAll", notificationsAll)//notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/nickname")
    public ModelAndView nickname(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("nickname");
    }
}
