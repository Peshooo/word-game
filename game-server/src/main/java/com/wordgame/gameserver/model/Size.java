package com.wordgame.gameserver.model;

import java.io.Serializable;

public class Size implements Serializable, Comparable<Size> {
    private int height;
    private int width;

    public Size() {
    }

    public Size(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int compareTo(Size size) {
        int compareHeight = height - size.getHeight();

        if (compareHeight != 0) {
            return compareHeight;
        }

        return width - size.getWidth();
    }
}