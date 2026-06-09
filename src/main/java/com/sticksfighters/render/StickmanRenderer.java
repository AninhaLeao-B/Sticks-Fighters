package com.sticksfighters.render;

import java.awt.Color;
import java.awt.Graphics;

public class StickmanRenderer {

    public static void drawStickman(
            Graphics g,
            int x,
            int y,
            Color color,
            boolean facingRight,
            boolean attacking,
            boolean damaged,
            boolean crouching) {

        if (g == null) return;

        // Cor do stickman (amarelo quando toma dano)
        g.setColor(damaged ? Color.YELLOW : color);

        int headY = crouching ? y + 20 : y;
        int bodyY = crouching ? y + 45 : y + 35;

        // Cabeça
        g.drawOval(x + 10, headY, 35, 35);
        // Corpo
        g.drawLine(x + 27, headY + 35, x + 27, bodyY + 80);

        // Braços
        if (attacking) {
            g.drawLine(x + 27, headY + 45, x + 75, headY + 40); // soco pra frente
        } else if (facingRight) {
            g.drawLine(x + 27, headY + 45, x + 5, headY + 65);
            g.drawLine(x + 27, headY + 45, x + 55, headY + 40);
        } else {
            g.drawLine(x + 27, headY + 45, x + 50, headY + 65);
            g.drawLine(x + 27, headY + 45, x, headY + 40);
        }

        // Pernas
        if (crouching) {
            g.drawLine(x + 27, bodyY + 80, x + 12, bodyY + 95);
            g.drawLine(x + 27, bodyY + 80, x + 42, bodyY + 95);
        } else {
            g.drawLine(x + 27, bodyY + 80, x + 12, bodyY + 125);
            g.drawLine(x + 27, bodyY + 80, x + 45, bodyY + 125);
        }
    }
}