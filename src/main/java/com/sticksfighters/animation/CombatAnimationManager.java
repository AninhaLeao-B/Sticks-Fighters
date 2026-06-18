package com.sticksfighters.animation;

import java.util.Objects;

public class CombatAnimationManager {

    private boolean playerAttacking;
    private boolean playerJumping;
    private boolean playerCrouching;
    private boolean enemyAttacking;
    private boolean enemyDamaged;
    private boolean playerDamaged;

    private String currentAttackType = "punch";
    private long damageEndTime = 0;

    public void playPlayerAttackAnimation(String attackType) {
        this.currentAttackType = (attackType != null && attackType.toLowerCase().contains("kick")) 
                ? "kick" : "punch";
        
        playerAttacking = true;

        new Thread(() -> {
            try { Thread.sleep(300); } 
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            finally { playerAttacking = false; }
        }).start();
    }

    public String getCurrentAttackType() {
        return currentAttackType;
    }

    public void playDamageAnimation() {
        playerDamaged = true;
        damageEndTime = System.currentTimeMillis() + 450;

        new Thread(() -> {
            try { Thread.sleep(450); } 
            catch (InterruptedException ignored) {}
            playerDamaged = false;
        }).start();
    }

    public boolean isPlayerDamaged() {
        if (playerDamaged && System.currentTimeMillis() > damageEndTime) {
            playerDamaged = false;
        }
        return playerDamaged;
    }

    public void triggerEnemyDamage() {
        enemyDamaged = true;
        new Thread(() -> {
            try { Thread.sleep(300); } 
            catch (InterruptedException ignored) {}
            enemyDamaged = false;
        }).start();
    }

    public void playEnemyAttackAnimation(Runnable attackLogic) {
        Objects.requireNonNull(attackLogic);

        new Thread(() -> {
            try {
                Thread.sleep(400);
                enemyAttacking = true;
                attackLogic.run();
                Thread.sleep(300);
                enemyAttacking = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void setJumping(boolean jumping) { this.playerJumping = jumping; }
    public void setCrouching(boolean crouching) { this.playerCrouching = crouching; }

    public boolean isPlayerAttacking() { return playerAttacking; }
    public boolean isPlayerJumping() { return playerJumping; }
    public boolean isPlayerCrouching() { return playerCrouching; }
    public boolean isEnemyAttacking() { return enemyAttacking; }
    public boolean isEnemyDamaged() { return enemyDamaged; }

    public void reset() {
        playerAttacking = false;
        playerJumping = false;
        playerCrouching = false;
        enemyAttacking = false;
        enemyDamaged = false;
        playerDamaged = false;
        currentAttackType = "punch";
    }
}