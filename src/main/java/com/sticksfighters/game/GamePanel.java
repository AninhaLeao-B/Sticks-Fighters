package com.sticksfighters.game;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.combat.CombatManager;
import com.sticksfighters.controller.GameController;
import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.input.PlayerInputHandler;
import com.sticksfighters.physics.PlayerMovement;
import com.sticksfighters.render.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private static final int ENEMY_X = 550;

    private final GameController controller;
    private final String backgroundPath;

    private PlayerMovement movement;
    private final PlayerInputHandler inputHandler;
    private CombatManager combatManager;
    private final CombatAnimationManager animations;

    private final Timer gameLoop;

    // Pause Menu
    private PauseMenu pauseMenu;
    private boolean isPaused = false;

    // Construtor antigo (compatibilidade)
    public GamePanel() {
        this(new GameController(), "/backgrounds/arena_esgoto.png");
    }

    public GamePanel(FighterData playerData) {
        this(new GameController(playerData), "/backgrounds/arena_esgoto.png");
    }

    // Novo construtor principal com background
    public GamePanel(FighterData playerData, String backgroundPath) {
        this(new GameController(playerData), backgroundPath);
    }

    public GamePanel(GameController controller, String backgroundPath) {
        this.controller = controller;
        this.backgroundPath = (backgroundPath != null) ? backgroundPath : "/backgrounds/arena_esgoto.png";

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
            drawBackground(g);
            pauseMenu.paintComponent(g);
            return;
        }

        // Jogo normal
        drawBackground(g);

        // Desenha o jogador com os sprites dele
        CharacterRenderer.drawPlayer(g, movement, animations, controller.getPlayer());
        
        // 🎯 A MÁGICA AQUI: Passando o objeto Fighter do inimigo para carregar os sprites do Corno_Vei!
        CharacterRenderer.drawEnemy(g, ENEMY_X, animations, controller.getEnemy());

        drawHUD(g);
        GameOverRenderer.draw(g, controller.isGameOver(), 
                             controller.getResultMessage(), getWidth(), getHeight());
    }

    private void drawBackground(Graphics g) {
        BufferedImage bg = ImageLoader.loadImage(backgroundPath);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        } else {
            BackgroundRenderer.drawArena(g, getWidth(), getHeight());
        }
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

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            togglePause();
            return;
        }

        if (isPaused) return;

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controller.getPlayer().setBlocking(true);
        }

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

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controller.getPlayer().setBlocking(false);
        }

        inputHandler.handleKeyReleased(e, movement::stopCrouching);
    }

    private void togglePause() {
        isPaused = !isPaused;

        if (isPaused) {
            if (pauseMenu == null) {
                pauseMenu = new PauseMenu(
                    this::resumeGame,
                    this::returnToSelection,             
                    this::returnToStageSelection,        
                    Main::showMainMenu                   
                );
            }
            pauseMenu.setSize(getSize());
            add(pauseMenu);
            pauseMenu.requestFocusInWindow();
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
        requestFocusInWindow();   // Devolve o foco para o jogo
    }
    
    private void returnToSelection() {
        Main.returnToSelection();   // Chama o método estático do Main
    }

    private void resetGame() {
    	controller.resetGame();
        controller.getPlayer().setBlocking(false); // 🛡️ Garante guarda aberta no reset
        movement = new PlayerMovement(150, 220, controller.getPlayer().getSpeed());
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
    
    private void returnToStageSelection() {
        resumeGame(); // fecha o pause primeiro
        Main.returnToStageSelection(); // chama o método que vamos criar no Main
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}