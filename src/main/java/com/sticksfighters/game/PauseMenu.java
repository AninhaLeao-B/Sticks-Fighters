package com.sticksfighters.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PauseMenu extends JPanel {

    private final Runnable onResume;
    private final Runnable onReturnToSelection;
    private final Runnable onReturnToStageSelection;
    private final Runnable onReturnToMainMenu;

    private int selectedOption = 0;
    private final String[] options = {
        "Continuar Jogando",
        "Voltar à Seleção de Personagem",
        "Voltar à Seleção de Fase",
        "Voltar ao Menu Inicial",
        "Sair do Jogo"
    };

    public PauseMenu(Runnable onResume, 
                     Runnable onReturnToSelection,
                     Runnable onReturnToStageSelection,
                     Runnable onReturnToMainMenu) {
        
        this.onResume = onResume;
        this.onReturnToSelection = onReturnToSelection;
        this.onReturnToStageSelection = onReturnToStageSelection;
        this.onReturnToMainMenu = onReturnToMainMenu;

        setBackground(new Color(0, 0, 0, 200));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> 
                        selectedOption = (selectedOption - 1 + options.length) % options.length;
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> 
                        selectedOption = (selectedOption + 1) % options.length;
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> executeOption();
                    case KeyEvent.VK_ESCAPE -> onResume.run();
                }
                repaint();
            }
        });
    }

    private void executeOption() {
        switch (selectedOption) {
            case 0 -> onResume.run();
            case 1 -> onReturnToSelection.run();
            case 2 -> onReturnToStageSelection.run();
            case 3 -> onReturnToMainMenu.run();
            case 4 -> System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 48f));
        g.drawString("PAUSA", getWidth()/2 - 80, 140);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 26f));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.YELLOW);
                g.drawString("→ " + options[i], getWidth()/2 - 200, 230 + i * 55);
            } else {
                g.setColor(Color.WHITE);
                g.drawString(options[i], getWidth()/2 - 170, 230 + i * 55);
            }
        }

        g.setColor(Color.LIGHT_GRAY);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 18f));
        g.drawString("ESC - Fechar Menu", getWidth()/2 - 100, getHeight() - 40);
    }
}