package com.sticksfighters.render;

import java.awt.Color;
import java.awt.Graphics;

public class BackgroundRenderer {

    private static final Color ARENA_BG = new Color(20, 20, 40);
    private static final Color FLOOR_COLOR = Color.DARK_GRAY;
    private static final int FLOOR_HEIGHT = 50;
    private static final int FLOOR_Y = 380;

    public static void drawArena(Graphics g, int width, int height) {
        if (g == null) return;

        // Céu / fundo
        g.setColor(ARENA_BG);
        g.fillRect(0, 0, width, height);

        // Chão
        g.setColor(FLOOR_COLOR);
        g.fillRect(0, FLOOR_Y, width, FLOOR_HEIGHT);
    }
}