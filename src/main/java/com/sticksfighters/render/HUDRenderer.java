package com.sticksfighters.render;

import com.sticksfighters.fighters.Fighter;

import java.awt.Color;
import java.awt.Graphics;

public class HUDRenderer {

    private static final int BAR_WIDTH = 200;
    private static final Color TEXT_COLOR = Color.WHITE;

    public static void drawHealthBar(Graphics g,
                                     Fighter fighter,
                                     int x,
                                     int y,
                                     Color barColor,
                                     String name) {

        if (g == null || fighter == null) return;

        int healthWidth = (int) (BAR_WIDTH * fighter.getHealthPercentage() / 100);

        // Nome
        g.setColor(TEXT_COLOR);
        g.drawString(name, x, y - 10);

        // Borda da barra
        g.drawRect(x, y, BAR_WIDTH, 25);

        // Barra de vida
        g.setColor(barColor);
        g.fillRect(x, y, healthWidth, 25);

        // Texto de vida
        g.setColor(TEXT_COLOR);
        String healthText = fighter.getHealth() + "/" + fighter.getMaxHealth();
        g.drawString(healthText, x + 75, y + 18);
    }

    public static void drawEnergyBar(Graphics g,
                                     Fighter fighter,
                                     int x,
                                     int y) {

        if (g == null || fighter == null) return;

        int energyWidth = (int) (BAR_WIDTH * fighter.getSpecialEnergy() / fighter.getMaxSpecialEnergy());

        g.setColor(TEXT_COLOR);
        g.drawString("ENERGIA", x, y - 5);

        // Borda
        g.drawRect(x, y, BAR_WIDTH, 18);

        // Barra de energia
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, energyWidth, 18);

        // Texto de energia
        g.setColor(TEXT_COLOR);
        String energyText = fighter.getSpecialEnergy() + "/" + fighter.getMaxSpecialEnergy();
        g.drawString(energyText, x + 75, y + 13);
    }
}