package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.Vector;

public class Position extends Vector {
    private float x;
    private float y;

    public Position() {
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(Vector delta) {
        x += delta.getX();
        y += delta.getY();
    }

    public void move(Vector delta, long factor) {
        x += delta.getX() * factor;
        y += delta.getY() * factor;
    }
}
