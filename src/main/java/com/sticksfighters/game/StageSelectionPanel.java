package com.sticksfighters.game;

import com.sticksfighters.render.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StageSelectionPanel extends JPanel {

    private final Runnable onStageSelected;
    private int selectedIndex = 0;

    private final List<Stage> stages = new ArrayList<>();

    public StageSelectionPanel(Runnable onStageSelected) {
        this.onStageSelected = onStageSelected;

        setBackground(new Color(10, 10, 25));
        setFocusable(true);

        // Adicione seus backgrounds aqui
        stages.add(new Stage("Marco Zero", "/backgrounds/marco_zero.png"));
        stages.add(new Stage("Bilola de Brennand", "/backgrounds/bilola_de_brennand.png"));
        stages.add(new Stage("Ratos Bar", "/backgrounds/ratos_bar.png"));
        stages.add(new Stage("Praia da Igrejinha", "/backgrounds/praia_da_igrejinha.png"));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> selectedIndex = (selectedIndex - 1 + stages.size()) % stages.size();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> selectedIndex = (selectedIndex + 1) % stages.size();
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> onStageSelected.run();
                    case KeyEvent.VK_ESCAPE -> Main.showGameModeScreen(); // voltar
                }
                repaint();
            }
        });
    }
    
    private void startGame() {   // ou o nome que você deu para o método de confirmar
        Stage selected = stages.get(selectedIndex);
        System.out.println("Fase selecionada: " + selected.name);
        
        onStageSelected.run();   // Isso vai chamar o startGame do Main
    }

    // Novo getter importante
    public String getSelectedBackgroundPath() {
        return stages.get(selectedIndex).imagePath;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Stage selected = stages.get(selectedIndex);

        // Desenha o background selecionado (preview)
        BufferedImage bg = ImageLoader.loadImage(selected.imagePath);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        }

        // Overlay escuro
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Título
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 42f));
        g.drawString("SELEÇÃO DE FASE", getWidth()/2 - 180, 80);

        // Nome da fase
        g.setFont(g.getFont().deriveFont(Font.BOLD, 32f));
        g.setColor(Color.YELLOW);
        g.drawString(selected.name, getWidth()/2 - g.getFontMetrics().stringWidth(selected.name)/2, 180);

        // Instruções
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 20f));
        g.drawString("← → : Mudar Fase    |    ENTER : Confirmar", getWidth()/2 - 220, 520);
    }

    private record Stage(String name, String imagePath) {}
}