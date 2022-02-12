package com.wordgame.webui.game.util;

import com.wordgame.webui.game.model.Position;
import com.wordgame.webui.game.model.Size;
import com.wordgame.webui.game.model.Vector;
import com.wordgame.webui.game.model.Word;

import java.util.Random;

public class WordFactory {
    private static final String HEX_SYMBOLS = "3456789abcdef";

    private static final int WORD_BOX_HEIGHT = 60;

    private static final int CANVAS_WIDTH = 1200;
    private static final int CANVAS_HEIGHT = 600;

    private static final int MINIMUM_SPEED = 2;
    private static final int MAXIMUM_SPEED = 6;

    private static final Random random = new Random();

    public static Word create(long timestamp) {
        String word = PlainWordProvider.getWord();
        String color = generateRandomColor();
        Size size = getWordSize(word);
        Position position = generatePositionBySize(size);
        Vector velocity = generateVelocity();

        return Word.builder()
                .word(word)
                .color(color)
                .size(size)
                .position(position)
                .movePixelsPerMillisecond(velocity)
                .build(timestamp);
    }

    private static String generateRandomColor() {
        String color = "#";

        for (int i = 0; i < 6; i++) {
            color += HEX_SYMBOLS.charAt(random.nextInt(HEX_SYMBOLS.length()));
        }

        return color;
    }

    private static Size getWordSize(String word) {
        return new Size(WORD_BOX_HEIGHT, WidthMeasurer.measureWordWidth(word));
    }

    private static Position generatePositionBySize(Size size) {
        return new Position(
                random.nextInt(CANVAS_WIDTH - size.getWidth() + 1),
                random.nextInt(CANVAS_HEIGHT - size.getHeight() + 1));
    }

    private static Vector generateVelocity() {
        return new Vector(
                randomSignedIntInAbsoluteRange(MINIMUM_SPEED, MAXIMUM_SPEED) / 32f,
                randomSignedIntInAbsoluteRange(MINIMUM_SPEED, MAXIMUM_SPEED) / 32f);
    }

    private static int randomSignedIntInAbsoluteRange(int low, int up) {
        int result = randomIntInRange(low, up);

        return random.nextBoolean() ? result : -result;
    }

    private static int randomIntInRange(int low, int up) {
        return low + random.nextInt(up - low + 1);
    }
}