package com.wordgame.webui.game.model;

import java.io.Serializable;

public class Vector implements Serializable, Comparable<Vector> {
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

    @Override
    public int compareTo(Vector vector) {
        float compareX = x - vector.getX();

        if (compareX != 0) {
            return compareX < 0 ? -1 : 1;
        }

        float compareY = y - vector.getY();

        if (compareY == 0) {
            return 0;
        }

        return compareY < 0 ? -1 : 1;
    }
}