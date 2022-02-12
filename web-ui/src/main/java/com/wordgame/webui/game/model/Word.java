package com.wordgame.webui.game.model;

import java.io.Serializable;

public class Word implements Serializable {
    private String word;
    private String color;
    private Size size;
    private Position position;
    private Vector movePixelsPerMillisecond;

    private long lastMoveTimestamp;

    private Word() {
    }

    public String getWord() {
        return word;
    }

    public Size getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public Vector getMovePixelsPerMillisecond() {
        return movePixelsPerMillisecond;
    }

    public long getLastMoveTimestamp() {
        return lastMoveTimestamp;
    }

    public void move(long timestamp) {
        long millisecondsSinceLastMove = timestamp - lastMoveTimestamp;
        position.move(movePixelsPerMillisecond, millisecondsSinceLastMove);
        lastMoveTimestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Word word;

        public Builder() {
            word = new Word();
        }

        public Builder word(String word) {
            this.word.word = word;

            return this;
        }

        public Builder color(String color) {
            word.color = color;

            return this;
        }

        public Builder size(Size size) {
            word.size = size;

            return this;
        }

        public Builder position(Position position) {
            word.position = position;

            return this;
        }

        public Builder movePixelsPerMillisecond(Vector movePixelsPerMillisecond) {
            word.movePixelsPerMillisecond = movePixelsPerMillisecond;

            return this;
        }

        public Word build(long timestamp) {
            word.lastMoveTimestamp = timestamp;

            return word;
        }
    }
}
