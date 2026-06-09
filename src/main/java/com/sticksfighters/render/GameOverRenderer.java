package com.sticksfighters.render;

import java.awt.Color;
import java.awt.Graphics;

public class GameOverRenderer {

    private static final Color OVERLAY_COLOR = new Color(0, 0, 0, 180);
    private static final Color TEXT_COLOR = Color.WHITE;

    public static void draw(Graphics g,
                            boolean gameOver,
                            String resultMessage,
                            int screenWidth,
                            int screenHeight) {

        if (!gameOver || g == null || resultMessage == null) {
            return;
        }

        // Overlay escuro
        g.setColor(OVERLAY_COLOR);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Texto principal
        g.setColor(TEXT_COLOR);
        g.setFont(g.getFont().deriveFont(48f));
        int textWidth = g.getFontMetrics().stringWidth(resultMessage);
        g.drawString(resultMessage,
                     screenWidth / 2 - textWidth / 2,
                     screenHeight / 2 - 20);

        // Instrução de restart
        g.setFont(g.getFont().deriveFont(20f));
        String restartText = "Pressione R para reiniciar";
        textWidth = g.getFontMetrics().stringWidth(restartText);
        g.drawString(restartText,
                     screenWidth / 2 - textWidth / 2,
                     screenHeight / 2 + 60);
    }
}