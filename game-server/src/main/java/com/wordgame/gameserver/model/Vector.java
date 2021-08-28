package com.wordgame.gameserver.model;

import java.io.Serializable;

public class Vector implements Serializable {
    private float x;
    private float y;

    public Vector() {
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}