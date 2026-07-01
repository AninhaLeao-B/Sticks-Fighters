package com.sticksfighters.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInputHandlerTest {

    private PlayerInputHandler handler;
    private AtomicBoolean moveLeftCalled, moveRightCalled, jumpCalled, crouchCalled;
    private AtomicBoolean weakPunchCalled, strongPunchCalled, weakKickCalled, strongKickCalled;
    private AtomicBoolean resetCalled;

    @BeforeEach
    void setUp() {
        handler = new PlayerInputHandler();
        moveLeftCalled = new AtomicBoolean(false);
        moveRightCalled = new AtomicBoolean(false);
        jumpCalled = new AtomicBoolean(false);
        crouchCalled = new AtomicBoolean(false);
        weakPunchCalled = new AtomicBoolean(false);
        strongPunchCalled = new AtomicBoolean(false);
        weakKickCalled = new AtomicBoolean(false);
        strongKickCalled = new AtomicBoolean(false);
        resetCalled = new AtomicBoolean(false);
    }

    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(new java.awt.Component() {}, 0, 0, 0, keyCode, ' ');
    }

    @Test
    void deveChamarMoveLeftQuandoApertarA() {
        handler.handleKeyPressed(createKeyEvent(KeyEvent.VK_A),
                () -> moveLeftCalled.set(true), () -> {}, () -> {}, () -> {}, () -> {}, () -> {}, () -> {}, () -> {}, () -> {}, false);

        assertTrue(moveLeftCalled.get());
    }

    @Test
    void deveChamarAtaquesCorretamente() {
        handler.handleKeyPressed(createKeyEvent(KeyEvent.VK_U), ()->{}, ()->{}, ()->{}, ()->{}, 
                () -> weakPunchCalled.set(true), ()->{}, ()->{}, ()->{}, ()->{}, false);
        handler.handleKeyPressed(createKeyEvent(KeyEvent.VK_O), ()->{}, ()->{}, ()->{}, ()->{}, 
                ()->{}, () -> strongPunchCalled.set(true), ()->{}, ()->{}, ()->{}, false);

        assertTrue(weakPunchCalled.get());
        assertTrue(strongPunchCalled.get());
    }

    @Test
    void deveChamarResetApenasQuandoGameOver() {
        handler.handleKeyPressed(createKeyEvent(KeyEvent.VK_R), ()->{}, ()->{}, ()->{}, ()->{}, 
                ()->{}, ()->{}, ()->{}, ()->{}, () -> resetCalled.set(true), true);

        assertTrue(resetCalled.get());
    }

    @Test
    void naoDeveExecutarComandosDuranteGameOver() {
        handler.handleKeyPressed(createKeyEvent(KeyEvent.VK_A), 
                () -> moveLeftCalled.set(true), ()->{}, ()->{}, ()->{}, ()->{}, ()->{}, ()->{}, ()->{}, ()->{}, true);

        assertFalse(moveLeftCalled.get()); // não deve mover durante game over
    }

    @Test
    void devePararDeAgacharQuandoSoltarS() {
        handler.handleKeyReleased(createKeyEvent(KeyEvent.VK_S), () -> crouchCalled.set(true));
        assertTrue(crouchCalled.get());
    }
}