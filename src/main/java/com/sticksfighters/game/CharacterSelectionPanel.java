package com.sticksfighters.game;

import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.fighters.Fighters;
import com.sticksfighters.render.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class CharacterSelectionPanel extends JPanel {

    private final List<FighterData> fighters;
    private int selectedIndex = 0;
    private final Runnable onCharacterSelected;

    public CharacterSelectionPanel(Runnable onCharacterSelected) {
        this.fighters = Fighters.getAllFighters();
        this.onCharacterSelected = onCharacterSelected;

        setBackground(new Color(20, 20, 40));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> selectedIndex = (selectedIndex - 1 + fighters.size()) % fighters.size();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> selectedIndex = (selectedIndex + 1) % fighters.size();
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> startGame();
                }
                repaint();
            }
        });
    }

    private FighterData selectedFighter;

    private void startGame() {
        selectedFighter = fighters.get(selectedIndex);
        System.out.println("✅ Selecionado: " + selectedFighter.getName());
        onCharacterSelected.run();
    }

    // Novo método getter
    public FighterData getSelectedFighter() {
        return selectedFighter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(48f));
        g.drawString("ESCOLHA SEU LUTADOR", getWidth()/2 - 280, 100);

        for (int i = 0; i < fighters.size(); i++) {
            FighterData f = fighters.get(i);
            int x = 150 + i * 300;

            // Destaque no selecionado
            if (i == selectedIndex) {
                g.setColor(Color.YELLOW);
                g.drawRect(x - 20, 180, 220, 280);
            }

            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(28f));
            g.drawString(f.getName(), x + 30, 220);

            // Tenta carregar uma imagem (idle)
            String spritePath = "/sprites/" + f.getSpriteFolder() + "/idle/01.png";
            Image img = ImageLoader.loadImage(spritePath);
            if (img != null) {
                g.drawImage(img, x + 40, 250, 140, 140, null);
            } else {
                // Fallback visual
                g.setColor(i == 0 ? Color.PINK : Color.ORANGE);
                g.fillRect(x + 50, 260, 120, 140);
            }

            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(18f));
            g.drawString("Vida: " + f.getMaxHealth(), x + 40, 430);
            g.drawString("Força: " + f.getStrength(), x + 40, 460);
        }

        g.setFont(g.getFont().deriveFont(22f));
        g.drawString("← → : Mudar   |   ENTER : Confirmar", getWidth()/2 - 220, 550);
    }
}