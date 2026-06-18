package com.sticksfighters.game;

import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.fighters.Fighters;
import javax.swing.*;

public class Main {

    private static JFrame window;
    private static MainMenuPanel mainMenuPanel;
    private static GameModePanel gameModePanel;
    private static CharacterSelectionPanel selectionPanel;
    private static StageSelectionPanel stageSelectionPanel;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        window = new JFrame("Stick Fighters");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);

        showMainMenu();
        window.setVisible(true);
    }

    public static void showMainMenu() {
        mainMenuPanel = new MainMenuPanel(Main::showGameModeScreen);
        window.setContentPane(mainMenuPanel);
        window.revalidate();
        mainMenuPanel.requestFocusInWindow();
    }

    public static void showGameModeScreen() {
        gameModePanel = new GameModePanel(
            Main::showSelectionScreen,      // Modo Treino
            Main::showTorneioScreen,
            Main::showOptionsScreen
        );
        window.setContentPane(gameModePanel);
        window.revalidate();
        gameModePanel.requestFocusInWindow();
    }

    public static void showSelectionScreen() {
        selectionPanel = new CharacterSelectionPanel(Main::showStageSelectionScreen);
        window.setContentPane(selectionPanel);
        window.revalidate();
        selectionPanel.requestFocusInWindow();
    }

    public static void showStageSelectionScreen() {
        stageSelectionPanel = new StageSelectionPanel(Main::startGameWithStage);
        window.setContentPane(stageSelectionPanel);
        window.revalidate();
        stageSelectionPanel.requestFocusInWindow();
    }

    private static void startGameWithStage() {
        FighterData chosen = selectionPanel.getSelectedFighter();
        if (chosen == null) chosen = Fighters.rataCamponesa();

        String backgroundPath = stageSelectionPanel.getSelectedBackgroundPath();

        gamePanel = new GamePanel(chosen, backgroundPath);
        window.setContentPane(gamePanel);
        window.revalidate();
        gamePanel.requestFocusInWindow();
    }

    private static void showTorneioScreen() {
        JOptionPane.showMessageDialog(window, "Modo Torneio\n\nEm desenvolvimento...", 
                                     "Stick Fighters", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showOptionsScreen() {
        JOptionPane.showMessageDialog(window, "Opções\n\nEm desenvolvimento...", 
                                     "Stick Fighters", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void returnToSelection() {
        if (gamePanel != null) {
            window.remove(gamePanel);
            gamePanel = null;
        }
        showSelectionScreen();
    }
    
    public static void returnToStageSelection() {
        if (gamePanel != null) {
            window.remove(gamePanel);
            gamePanel = null;
        }
        showStageSelectionScreen();   // volta direto para a seleção de fase
    }
}