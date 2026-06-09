package com.sticksfighters.game;

import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.fighters.Fighters;

import javax.swing.*;

public class Main {

    private static JFrame window;
    private static CharacterSelectionPanel selectionPanel;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        window = new JFrame("Stick Fighters");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);

        showSelectionScreen();
        window.setVisible(true);
    }

    private static void showSelectionScreen() {
        if (gamePanel != null) {
            window.remove(gamePanel);
        }

        selectionPanel = new CharacterSelectionPanel(Main::startGame);
        window.setContentPane(selectionPanel);
        window.revalidate();
        selectionPanel.requestFocusInWindow();
    }

    private static void startGame() {
        FighterData chosen = selectionPanel.getSelectedFighter();
        if (chosen == null) chosen = Fighters.rataCamponesa(); // fallback

        if (selectionPanel != null) {
            window.remove(selectionPanel);
        }

        gamePanel = new GamePanel(chosen);   // ← Passando o lutador
        window.setContentPane(gamePanel);
        window.revalidate();
        gamePanel.requestFocusInWindow();
    }

    public static void returnToSelection() {
        if (gamePanel != null) {
            window.remove(gamePanel);
            gamePanel = null;
        }
        showSelectionScreen();
    }
}