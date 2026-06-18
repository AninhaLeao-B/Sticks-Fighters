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
        this.controller = Objects.requireNonNull(controller);
        this.movement = Objects.requireNonNull(movement);
        this.animations = Objects.requireNonNull(animations);
        this.repaintCallback = Objects.requireNonNull(repaintCallback);
        this.enemyX = enemyX;
    }

    public void playerAttack(String type) {
        // Congela a distância exata do momento do golpe do jogador
        final int lockedDistance = getCurrentDistance();

        boolean playerHit = controller.playerAttack(type, movement.isJumping(), movement.isCrouching(), lockedDistance);
        
        animations.playPlayerAttackAnimation(type);

        if (playerHit) {
            animations.triggerEnemyDamage(); 
            repaintCallback.run(); 
        }

        if (controller.getEnemy().isAlive()) {
            animations.playEnemyAttackAnimation(() -> {
                // Usa a distância congelada (lockedDistance) para o revide
                boolean enemyHit = controller.enemyAttack(lockedDistance, movement.isJumping(), movement.isCrouching());
                
                if (enemyHit) {
                    animations.playDamageAnimation(); // Animação de dano no Chicão
                }
                
                repaintCallback.run();
            });
        } else {
            repaintCallback.run();
        }
    }

    public void triggerPlayerDamage() {
        animations.playDamageAnimation();
    }

    public int getCurrentDistance() {
        return Math.abs(movement.getPlayerX() - enemyX);
    }
}