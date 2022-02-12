package com.wordgame.webui.insights;

import com.wordgame.webui.configuration.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/insights")
public class InsightsController {
    @Autowired
    private DailyCountersService dailyCountersService;

    @GetMapping
    @ResponseBody
    public DailyCountersResponse get(HttpServletRequest request) {
        if (!request.getHeader("X-Admin").equals(Password.PASSWORD)) {
            throw new RuntimeException("Unauthorized.");
        }

        return dailyCountersService.get();
    }
}
