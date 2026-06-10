package com.sticksfighters.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameModePanel extends JPanel {

    private final Runnable onTreino;
    private final Runnable onTorneio;
    private final Runnable onOpcoes;

    private int selectedOption = 0;
    private final String[] options = {
        "Modo Treino",
        "Modo Torneio",
        "Opções"
    };

    public GameModePanel(Runnable onTreino, Runnable onTorneio, Runnable onOpcoes) {
        this.onTreino = onTreino;
        this.onTorneio = onTorneio;
        this.onOpcoes = onOpcoes;

        setBackground(new Color(10, 10, 25));
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
                    case KeyEvent.VK_ESCAPE -> Main.showMainMenu(); // voltar pro menu principal
                }
                repaint();
            }
        });
    }

    private void executeOption() {
        switch (selectedOption) {
            case 0 -> onTreino.run();      // Modo Treino
            case 1 -> onTorneio.run();     // Torneio
            case 2 -> onOpcoes.run();      // Opções
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        g.setColor(new Color(10, 10, 25));
        g.fillRect(0, 0, width, height);

        // Título
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 42f));
        String title = "MODO DE JOGO";
        int titleW = g.getFontMetrics().stringWidth(title);
        g.drawString(title, width / 2 - titleW / 2, 120);

        // Opções
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 28f));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.YELLOW);
                g.drawString("→ " + options[i], width / 2 - 140, 220 + i * 70);
            } else {
                g.setColor(Color.WHITE);
                g.drawString(options[i], width / 2 - 100, 220 + i * 70);
            }
        }

        // Instruções
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 18f));
        g.drawString("↑ ↓ : Navegar    |    ENTER : Selecionar    |    ESC : Voltar", 
                     width / 2 - 220, height - 50);
    }
}