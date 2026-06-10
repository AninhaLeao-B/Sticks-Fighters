package com.sticksfighters.animation;

import java.util.Objects;

public class CombatAnimationManager {

    private boolean playerAttacking;
    private boolean playerJumping;
    private boolean playerCrouching;
    private boolean enemyAttacking;
    private boolean enemyDamaged;

    private String currentAttackType = "punch";

    /**
     * Inicia animação de ataque do jogador (com tipo punch/kick)
     */
    public void playPlayerAttackAnimation(String attackType) {
        this.currentAttackType = (attackType != null && attackType.toLowerCase().contains("kick")) 
                ? "kick" : "punch";
        
        playerAttacking = true;

        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                playerAttacking = false;
            }
        }).start();
    }

    public String getCurrentAttackType() {
        return currentAttackType;
    }

    /**
     * Inicia animação de ataque do inimigo com callback
     */
    public void playEnemyAttackAnimation(Runnable attackLogic) {
        Objects.requireNonNull(attackLogic, "Attack logic cannot be null");

        new Thread(() -> {
            try {
                Thread.sleep(400);
                enemyAttacking = true;

                attackLogic.run();

                Thread.sleep(300);
                enemyAttacking = false;
                enemyDamaged = true;

                Thread.sleep(150);
                enemyDamaged = false;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // Setters
    public void setJumping(boolean jumping) {
        this.playerJumping = jumping;
    }

    public void setCrouching(boolean crouching) {
        this.playerCrouching = crouching;
    }

    // Getters
    public boolean isPlayerAttacking() { return playerAttacking; }
    public boolean isPlayerJumping() { return playerJumping; }
    public boolean isPlayerCrouching() { return playerCrouching; }
    public boolean isEnemyAttacking() { return enemyAttacking; }
    public boolean isEnemyDamaged() { return enemyDamaged; }

    public void reset() {
        this.playerAttacking = false;
        this.playerJumping = false;
        this.playerCrouching = false;
        this.enemyAttacking = false;
        this.enemyDamaged = false;
        this.currentAttackType = "punch";
    }
}