package com.sticksfighters.game;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.combat.CombatManager;
import com.sticksfighters.controller.GameController;
import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.input.PlayerInputHandler;
import com.sticksfighters.physics.PlayerMovement;
import com.sticksfighters.render.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private static final int ENEMY_X = 550;

    private final GameController controller;
    private PlayerMovement movement;
    private final PlayerInputHandler inputHandler;
    private CombatManager combatManager;
    private final CombatAnimationManager animations;

    private final Timer gameLoop;

    // Pause Menu
    private PauseMenu pauseMenu;
    private boolean isPaused = false;

    public GamePanel() {
        this(new GameController());
    }
    
    public GamePanel(FighterData playerData) {
        this(new GameController(playerData));
    }

    public GamePanel(GameController controller) {
        this.controller = controller;

        setBackground(new Color(20, 20, 40));
        setFocusable(true);
        addKeyListener(this);

        movement = new PlayerMovement(150, 220, controller.getPlayer().getSpeed());
        animations = new CombatAnimationManager();
        inputHandler = new PlayerInputHandler();

        combatManager = new CombatManager(
                controller,
                movement,
                animations,
                ENEMY_X,
                this::repaint
        );

        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPaused) return;

        movement.update();
        animations.setJumping(movement.isJumping());
        animations.setCrouching(movement.isCrouching());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isPaused && pauseMenu != null) {
            pauseMenu.paintComponent(g);   // desenha o menu por cima
            return;
        }

        BackgroundRenderer.drawArena(g, getWidth(), getHeight());

        CharacterRenderer.drawPlayer(g, movement, animations, controller.getPlayer());
        CharacterRenderer.drawEnemy(g, ENEMY_X, animations);

        drawHUD(g);
        GameOverRenderer.draw(g, controller.isGameOver(), 
                             controller.getResultMessage(), getWidth(), getHeight());
    }

    private void drawHUD(Graphics g) {
        HUDRenderer.drawHealthBar(g, controller.getPlayer(), 80, 40, Color.CYAN, 
                                  controller.getPlayer().getName());
        HUDRenderer.drawHealthBar(g, controller.getEnemy(), 520, 40, Color.RED, 
                                  controller.getEnemy().getName());
        HUDRenderer.drawEnergyBar(g, controller.getPlayer(), 80, 75);
    }

    // ==================== INPUT ====================
    @Override
    public void keyPressed(KeyEvent e) {
        if (controller.isGameOver()) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                resetGame();
            }
            return;
        }

        // Pause com ESC
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            togglePause();
            return;
        }

        if (isPaused) {
            // O PauseMenu cuida dos inputs dele
            return;
        }

     // Atualiza estados de animação
        animations.setJumping(movement.isJumping());
        animations.setCrouching(movement.isCrouching());
        
        inputHandler.handleKeyPressed(
            e,
            movement::moveLeft,
            () -> movement.moveRight(ENEMY_X),
            () -> { if (!movement.isJumping()) movement.jump(); },
            movement::crouch,
            () -> combatManager.playerAttack("punch_weak"),
            () -> combatManager.playerAttack("punch_strong"),
            () -> combatManager.playerAttack("kick_weak"),
            () -> combatManager.playerAttack("kick_strong"),
            this::resetGame,
            controller.isGameOver()
        );
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isPaused) return;

        // Parar movimento contínuo
        if (e.getKeyCode() == KeyEvent.VK_A) {
            movement.stopMovingLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            movement.stopMovingRight();
        }

        inputHandler.handleKeyReleased(e, movement::stopCrouching);
    }

    private void togglePause() {
        isPaused = !isPaused;

        if (isPaused) {
            if (pauseMenu == null) {
                pauseMenu = new PauseMenu(
                    this::resumeGame,
                    this::returnToSelection
                );
            }
            pauseMenu.setSize(getSize());
            add(pauseMenu);
            pauseMenu.requestFocusInWindow();   // ← Isso é o mais importante
        } else {
            if (pauseMenu != null) {
                remove(pauseMenu);
            }
        }
        repaint();
    }

    private void resumeGame() {
        isPaused = false;
        if (pauseMenu != null) {
            remove(pauseMenu);
        }
        repaint();
        requestFocusInWindow();   // Devolve o foco pro GamePanel
    }

    private void returnToSelection() {
        Main.returnToSelection();   // Chama o método estático do Main
    }

    private void resetGame() {
        controller.resetGame();
        movement = new PlayerMovement(150, 220, controller.getPlayer().getSpeed());

        combatManager = new CombatManager(
                controller,
                movement,
                animations,
                ENEMY_X,
                this::repaint
        );

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}