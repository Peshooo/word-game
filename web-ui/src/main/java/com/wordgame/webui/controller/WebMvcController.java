package com.wordgame.webui.controller;

import com.google.common.collect.ImmutableMap;
import com.wordgame.webui.model.GameRecord;
import com.wordgame.webui.service.GameServerRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class WebMvcController {
//    private final StandardRecordsDao standardRecordsDao;
//    private final SurvivalRecordsDao survivalRecordsDao;
//    private final NotificationsService notificationsService;

//    public WebController(StandardRecordsDao standardRecordsDao, SurvivalRecordsDao survivalRecordsDao, NotificationsService notificationsService) {
//        this.standardRecordsDao = standardRecordsDao;
//        this.survivalRecordsDao = survivalRecordsDao;
//        this.notificationsService = notificationsService;
//    }

    @Autowired
    private GameServerRestClient gameServerRestClient;

    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        List<GameRecord> standardTopFive = Collections.emptyList();//standardRecordsDao.getTopFiveLast24Hours();
        List<GameRecord> survivalTopFive = Collections.emptyList();// survivalRecordsDao.getTopFiveLast24Hours();

        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("standardRecords", standardTopFive)
                        .put("survivalRecords", survivalTopFive)
                        .put("notificationsCount", 0)// notificationsService.getNotifications().getMessages().size())
                        .put("notificationsAll", "")//notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("index", context);
    }

    @RequestMapping("/standard")
    public ModelAndView standard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "standard")
                        .put("gameServerRestClient", gameServerRestClient)
                        .put("notificationsCount", 0)// notificationsService.getNotifications().getMessages().size())
                        .put("notificationsAll", "")//notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/survival")
    public ModelAndView survival(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "survival")
                        .put("gameServerRestClient", gameServerRestClient)
                        .put("notificationsCount", 0)// notificationsService.getNotifications().getMessages().size())
                        .put("notificationsAll", "")//notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/nickname")
    public ModelAndView nickname(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("nickname");
    }
}
