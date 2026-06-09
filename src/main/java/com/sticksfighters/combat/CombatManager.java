package com.sticksfighters.combat;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.controller.GameController;
import com.sticksfighters.physics.PlayerMovement;

import java.util.Objects;

public class CombatManager {

    private final GameController controller;
    private final PlayerMovement movement;
    private final CombatAnimationManager animations;
    private final int enemyX;
    private final Runnable repaintCallback;

    public CombatManager(GameController controller,
                         PlayerMovement movement,
                         CombatAnimationManager animations,
                         int enemyX,
                         Runnable repaintCallback) {

        this.controller = Objects.requireNonNull(controller, "GameController cannot be null");
        this.movement = Objects.requireNonNull(movement, "PlayerMovement cannot be null");
        this.animations = Objects.requireNonNull(animations, "CombatAnimationManager cannot be null");
        this.repaintCallback = Objects.requireNonNull(repaintCallback, "Repaint callback cannot be null");
        this.enemyX = enemyX;
    }

    public void playerAttack(String attackType) {
        int distance = Math.abs(movement.getPlayerX() - enemyX);

        // Lógica de ataque (separada do visual)
        controller.playerAttack(
                attackType,
                movement.isJumping(),
                movement.isCrouching(),
                distance
        );

        animations.playPlayerAttackAnimation();

        if (controller.getEnemy().isAlive()) {
            animations.playEnemyAttackAnimation(() -> {
                controller.enemyAttack(distance);
                repaintCallback.run();
            });
        }
    }

    // Getter para testes
    public int getCurrentDistance() {
        return Math.abs(movement.getPlayerX() - enemyX);
    }
}