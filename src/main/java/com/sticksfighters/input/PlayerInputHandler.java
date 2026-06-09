package com.sticksfighters.input;

import java.awt.event.KeyEvent;

public class PlayerInputHandler {

    /**
     * Processa teclas pressionadas.
     */
    public void handleKeyPressed(
            KeyEvent e,
            Runnable moveLeft,
            Runnable moveRight,
            Runnable jump,
            Runnable crouch,
            Runnable weakPunch,
            Runnable strongPunch,
            Runnable weakKick,
            Runnable strongKick,
            Runnable resetGame,
            boolean gameOver) {

        int key = e.getKeyCode();

        // Game Over: só permite reset
        if (gameOver) {
            if (key == KeyEvent.VK_R) {
                resetGame.run();
            }
            return;
        }

        switch (key) {
            case KeyEvent.VK_A -> moveLeft.run();
            case KeyEvent.VK_D -> moveRight.run();
            case KeyEvent.VK_W -> jump.run();
            case KeyEvent.VK_S -> crouch.run();

            // Ataques
            case KeyEvent.VK_U -> weakPunch.run();
            case KeyEvent.VK_O -> strongPunch.run();
            case KeyEvent.VK_I -> weakKick.run();

            // Strong Kick (várias teclas opcionais)
            case KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L -> strongKick.run();

            default -> {} // tecla não mapeada
        }
    }

    /**
     * Processa teclas liberadas (atualmente só para sair do agachamento)
     */
    public void handleKeyReleased(KeyEvent e, Runnable stopCrouching) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            stopCrouching.run();
        }
    }
}