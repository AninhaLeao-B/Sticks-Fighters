package com.sticksfighters.physics;

import com.sticksfighters.fighters.Fighters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMovementTest {

    private PlayerMovement movement;

    @BeforeEach
    void setUp() {
        movement = new PlayerMovement(150, 220, 6);
    }

    @Test
    void deveMoverParaEsquerda() {
        int xInicial = movement.getPlayerX();
        movement.moveLeft();
        movement.update();
        assertTrue(movement.getPlayerX() < xInicial);
    }

    @Test
    void deveMoverParaDireita() {
        int xInicial = movement.getPlayerX();
        movement.moveRight(550);
        movement.update();
        assertTrue(movement.getPlayerX() > xInicial);
    }

    @Test
    void naoDevePassarDoLimiteEsquerdo() {
        movement = new PlayerMovement(30, 220, 6);
        movement.moveLeft();
        assertEquals(30, movement.getPlayerX());
    }

    @Test
    void naoDeveAtravessarInimigo() {
        movement = new PlayerMovement(460, 220, 6);
        movement.moveRight(550);
        assertEquals(460, movement.getPlayerX());
    }

    @Test
    void deveIniciarPuloCorretamente() {
        movement.jump();
        assertTrue(movement.isJumping());
    }

    @Test
    void naoDeveReiniciarPuloEnquantoEstaNoAr() {
        movement.jump();
        movement.jump(); // segunda chamada
        assertTrue(movement.isJumping());
    }

    @Test
    void deveAtualizarPosicaoDurantePulo() {
        int yInicial = movement.getPlayerY();
        movement.jump();
        movement.update();
        assertTrue(movement.getPlayerY() < yInicial);
    }

    @Test
    void deveVoltarAoChaoAposPulo() {
        int yInicial = movement.getPlayerY();
        movement.jump();

        for (int i = 0; i < 100; i++) {
            movement.update();
        }

        assertEquals(yInicial, movement.getPlayerY());
        assertFalse(movement.isJumping());
    }

    @Test
    void deveAgacharCorretamente() {
        movement.crouch();
        assertTrue(movement.isCrouching());
    }

    @Test
    void deveLevantarAoPararDeAgachar() {
        movement.crouch();
        movement.stopCrouching();
        assertFalse(movement.isCrouching());
    }

    @Test
    void deveMoverContinuamenteEnquantoTeclaPressionada() {
        movement.moveRight(550);
        int xInicial = movement.getPlayerX();

        movement.update();
        movement.update();

        assertTrue(movement.getPlayerX() > xInicial);
    }
}