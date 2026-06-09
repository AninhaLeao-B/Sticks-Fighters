package com.sticksfighters.physics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.sticksfighters.fighters.Fighters;

class PlayerMovementTest {

    private PlayerMovement movement;

    @BeforeEach
    void setUp() {
        movement = new PlayerMovement(
                150,
                220,
                Fighters.rataCamponesa().getSpeed());
    }

    // Movimento para esquerda
    @Test
    void moveLeftShouldDecreasePlayerX() {

        int initialX = movement.getPlayerX();

        movement.moveLeft();

        assertTrue(
                movement.getPlayerX() < initialX);
    }

    // Movimento para direita
    @Test
    void moveRightShouldIncreasePlayerX() {

        int initialX = movement.getPlayerX();

        movement.moveRight(550);

        assertTrue(
                movement.getPlayerX() > initialX);
    }

    // Não sai da tela pela esquerda
    @Test
    void playerShouldNotMoveBeyondLeftLimit() {

        movement = new PlayerMovement(
                30,
                220,
                Fighters.rataCamponesa().getSpeed());

        movement.moveLeft();

        assertEquals(
                30,
                movement.getPlayerX());
    }

    // Não atravessa o inimigo
    @Test
    void playerShouldNotCrossEnemy() {

        movement = new PlayerMovement(
                460,
                220,
                Fighters.rataCamponesa().getSpeed());

        movement.moveRight(550);

        assertEquals(
                460,
                movement.getPlayerX());
    }

    // Pulo inicia corretamente
    @Test
    void jumpShouldActivateJumpingState() {

        movement.jump();

        assertTrue(
                movement.isJumping());
    }

    // Update do pulo move o personagem para cima
    @Test
    void updateShouldMovePlayerUpDuringJump() {

        int initialY =
                movement.getPlayerY();

        movement.jump();
        movement.update();

        assertTrue(
                movement.getPlayerY() < initialY);
    }

    // Personagem volta ao chão
    @Test
    void playerShouldReturnToGroundAfterJump() {

        int initialY =
                movement.getPlayerY();

        movement.jump();

        for (int i = 0; i < 100; i++) {
            movement.update();
        }

        assertEquals(
                initialY,
                movement.getPlayerY());

        assertFalse(
                movement.isJumping());
    }

    // Personagem abaixa
    @Test
    void crouchShouldActivateCrouching() {

        movement.crouch();

        assertTrue(
                movement.isCrouching());
    }

    // Personagem levanta
    @Test
    void stopCrouchingShouldDeactivateCrouching() {

        movement.crouch();
        movement.stopCrouching();

        assertFalse(
                movement.isCrouching());
    }

    // Movimento respeita velocidade do personagem
    @Test
    void movementShouldRespectCharacterSpeed() {

        PlayerMovement movement =
                new PlayerMovement(
                        150,
                        220,
                        22);

        int initialX =
                movement.getPlayerX();

        movement.moveRight(550);

        assertEquals(
                initialX + 22,
                movement.getPlayerX());
    }

    // Não reinicia pulo enquanto já está pulando
    @Test
    void jumpShouldNotRestartWhileAlreadyJumping() {

        movement.jump();

        movement.jump();

        assertTrue(
                movement.isJumping());
    }
}