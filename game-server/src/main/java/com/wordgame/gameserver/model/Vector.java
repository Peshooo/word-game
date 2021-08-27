package com.wordgame.gameserver.model;

public class Vector {
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