package com.sticksfighters.combat;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.controller.GameController;
import com.sticksfighters.fighters.Fighters;
import com.sticksfighters.physics.PlayerMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class CombatManagerTest {

    private GameController controller;
    private PlayerMovement movement;
    private CombatAnimationManager animations;
    private CombatManager combatManager;
    private AtomicBoolean repaintCalled;

    @BeforeEach
    void setUp() {
        controller = new GameController();
        movement = new PlayerMovement(150, 220, 6);
        animations = new CombatAnimationManager();
        repaintCalled = new AtomicBoolean(false);

        combatManager = new CombatManager(
                controller,
                movement,
                animations,
                550,
                () -> repaintCalled.set(true)
        );
    }

    @Test
    void deveCriarCombatManagerSemErro() {
        assertNotNull(combatManager);
    }

    @Test
    void deveChamarPlayerAttackCorretamente() {
        assertDoesNotThrow(() -> {
            combatManager.playerAttack("punch_weak");
        });
    }

    @Test
    void deveRetornarDistanciaAtualCorretamente() {
        assertEquals(400, combatManager.getCurrentDistance()); // 550 - 150 = 400
    }

    @Test
    void deveTriggerarDanoNoPlayer() {
        assertDoesNotThrow(() -> {
            combatManager.triggerPlayerDamage();
        });
    }

    @Test
    void naoDeveLancarExcecaoComParametrosValidos() {
        assertDoesNotThrow(() -> new CombatManager(
                controller, movement, animations, 550, () -> {}));
    }
}