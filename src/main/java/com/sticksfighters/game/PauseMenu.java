package com.sticksfighters.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PauseMenu extends JPanel {

    private final Runnable onResume;
    private final Runnable onReturnToSelection;
    private int selectedOption = 0;
    private final String[] options = {"Continuar", "Voltar à Seleção", "Sair do Jogo"};

    public PauseMenu(Runnable onResume, Runnable onReturnToSelection) {
        this.onResume = onResume;
        this.onReturnToSelection = onReturnToSelection;

        setBackground(new Color(0, 0, 0, 200)); // semi-transparente
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> selectedOption = (selectedOption - 1 + options.length) % options.length;
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> selectedOption = (selectedOption + 1) % options.length;
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> executeOption();
                    case KeyEvent.VK_ESCAPE -> onResume.run();
                }
                repaint();
            }
        });
    }

    private void executeOption() {
        switch (selectedOption) {
            case 0 -> onResume.run();           // Continuar
            case 1 -> onReturnToSelection.run(); // Voltar à seleção
            case 2 -> System.exit(0);           // Sair
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(48f));
        g.drawString("PAUSA", getWidth()/2 - 80, 150);

        g.setFont(g.getFont().deriveFont(28f));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.YELLOW);
                g.drawString("→ " + options[i], getWidth()/2 - 120, 250 + i * 60);
            } else {
                g.setColor(Color.WHITE);
                g.drawString(options[i], getWidth()/2 - 100, 250 + i * 60);
            }
        }

        g.setFont(g.getFont().deriveFont(18f));
        g.drawString("ESC - Fechar menu", getWidth()/2 - 100, 480);
    }
}