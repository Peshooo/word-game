package com.wordgame.webui.game.service;

import com.wordgame.webui.game.service.manager.GamesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class DeleteScheduler {
    @Autowired
    private GamesManager gamesManager;

    private Queue<String> phase1 = new ConcurrentLinkedQueue<>();
    private Queue<String> phase2 = new ConcurrentLinkedQueue<>();

    public void schedule(String gameId) {
        phase1.add(gameId);
    }

    @Scheduled(fixedRate = 3000)
    public void advance() {
        phase2.forEach(gamesManager::delete);
        phase2.clear();
        phase2.addAll(phase1);
        phase1.removeAll(phase2);
    }
}
