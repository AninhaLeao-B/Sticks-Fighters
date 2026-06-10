package com.sticksfighters.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainMenuPanel extends JPanel {

    private final Runnable onStartGame;

    public MainMenuPanel(Runnable onStartGame) {
        this.onStartGame = onStartGame;

        setBackground(new Color(10, 10, 25)); // tom escuro de esgoto
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    onStartGame.run();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Fundo escuro
        g.setColor(new Color(10, 10, 25));
        g.fillRect(0, 0, width, height);

        // Título principal
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 52f));
        String title = "ESGOTOS DO RECIFE";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, width / 2 - titleWidth / 2, 180);

        // Subtítulo
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 24f));
        String subtitle = "Stick Fighters";
        int subWidth = g.getFontMetrics().stringWidth(subtitle);
        g.drawString(subtitle, width / 2 - subWidth / 2, 230);

        // Caixa do Start
        g.setColor(new Color(0, 180, 0));
        g.fillRoundRect(width / 2 - 120, 320, 240, 70, 20, 20);

        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 32f));
        String startText = "START";
        int startWidth = g.getFontMetrics().stringWidth(startText);
        g.drawString(startText, width / 2 - startWidth / 2, 365);

        // Instrução
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 18f));
        String instruction = "Pressione ENTER para começar";
        int instWidth = g.getFontMetrics().stringWidth(instruction);
        g.drawString(instruction, width / 2 - instWidth / 2, 480);

        // Detalhe visual (opcional)
        g.setColor(new Color(80, 80, 100));
        g.drawRect(80, 80, width - 160, height - 160);
    }
}