package com.sticksfighters.physics;

public class PlayerMovement {

    private static final double GRAVITY = 1.1;
    private static final int MIN_X = 30;
    private static final int MAX_DISTANCE_FROM_ENEMY = 85;

    private final int speed;
    private final int baseY;

    private int playerX;
    private int playerY;

    private boolean jumping;
    private boolean crouching;
    private double jumpVelocity;

    // Flags para movimentação contínua
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public PlayerMovement(int startX, int startY, int speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be positive");
        }
        this.speed = speed;
        this.baseY = startY;
        this.playerX = startX;
        this.playerY = startY;
        this.jumpVelocity = 0;
    }

    public void update() {
        // Velocidade do walk - valor fixo e ajustável (mais fácil de calibrar)
        int walkSpeed = 5;        // ← Mude esse número:
                                  // 4 = lento e fluido
                                  // 5 = bom equilíbrio
                                  // 6 = um pouco mais rápido

        if (movingLeft && playerX > MIN_X) {
            playerX -= walkSpeed;
        }
        if (movingRight && playerX < 550 - MAX_DISTANCE_FROM_ENEMY) {
            playerX += walkSpeed;
        }

        // Física do pulo
        if (jumping) {
            playerY += jumpVelocity;
            jumpVelocity += GRAVITY;

            if (playerY >= baseY) {
                playerY = baseY;
                jumping = false;
                jumpVelocity = 0;
            }
        }
    }

    // === Comandos de Movimento ===
    public void moveLeft() {
        movingLeft = true;
    }

    public void moveRight(int enemyX) {
        movingRight = true;
    }

    public void stopMovingLeft() {
        movingLeft = false;
    }

    public void stopMovingRight() {
        movingRight = false;
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            jumpVelocity = -19;
        }
    }

    public void crouch() {
        crouching = true;
    }

    public void stopCrouching() {
        crouching = false;
    }

    public boolean isMoving() {
        return movingLeft || movingRight;
    }

    // === Getters ===
    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
    public boolean isJumping() { return jumping; }
    public boolean isCrouching() { return crouching; }
    public int getSpeed() { return speed; }

    public void reset(int startX, int startY) {
        this.playerX = startX;
        this.playerY = startY;
        this.jumping = false;
        this.crouching = false;
        this.movingLeft = false;
        this.movingRight = false;
        this.jumpVelocity = 0;
    }
}