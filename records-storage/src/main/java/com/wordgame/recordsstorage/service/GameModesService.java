package com.wordgame.recordsstorage.service;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class GameModesService {
    public static List<String> getGameModes() {
        return ImmutableList.of("standard", "survival");
    }
}
