package com.wordgame.webui.insights;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class DailyCounterAspect {
    @Autowired
    private DailyCountersService dailyCountersService;

    @Around("execution(* *(..)) && @annotation(dailyCounter)")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint, DailyCounter dailyCounter) {
        dailyCountersService.count(dailyCounter.key());

        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
