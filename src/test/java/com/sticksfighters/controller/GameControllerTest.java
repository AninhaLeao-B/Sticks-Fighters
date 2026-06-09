package com.sticksfighters.controller;

import com.sticksfighters.fighters.Fighter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        controller = new GameController();
    }

    //ATAQUES BÁSICOS
    
    // O jogador pode atacar o inimigo quando estar dentro do range
    @Test
    void playerAttackShouldDamageEnemyWhenInRange() {
        int initialHealth = controller.getEnemy().getHealth();

        controller.playerAttack("punch_weak", false, false, 100);

        assertTrue(controller.getEnemy().getHealth() < initialHealth);
    }

    // O jogador não pode bater quando estar muito longe
    @Test
    void playerAttackShouldNotHitWhenTooFar() {
        int initialHealth = controller.getEnemy().getHealth();

        controller.playerAttack("punch_strong", false, false, 300);

        assertEquals(initialHealth, controller.getEnemy().getHealth());
    }

    //TIPOS DE ATAQUE
    
    // Pular atacando usar o golpe aéreo
    @Test
    void jumpingAttackShouldUseAerialAttacks() {
        int initialHealth = controller.getEnemy().getHealth();

        controller.playerAttack("punch", true, false, 150);

        assertTrue(controller.getEnemy().getHealth() < initialHealth);
    }
    
    // Abaixar atacando usando o ataque baixo
    @Test
    void crouchingAttackShouldUseLowAttacks() {
        int initialHealth = controller.getEnemy().getHealth();

        controller.playerAttack("kick", false, true, 100);

        assertTrue(controller.getEnemy().getHealth() < initialHealth);
    }

    //ENERGIA ESPECIAL

    // Usar o ataque especial consome energia
    @Test
    void specialAttackShouldConsumeEnergy() {
        // Dá uma quantidade boa de energia primeiro
        controller.getPlayer().gainSpecialEnergy(100);
        int initialEnergy = controller.getPlayer().getSpecialEnergy();

        controller.playerAttack("hadouken", false, false, 150);

        assertTrue(controller.getPlayer().getSpecialEnergy() < initialEnergy);
    }

    // Atacar ganha energia
    @Test
    void normalAttackShouldGainEnergy() {
        int initialEnergy = controller.getPlayer().getSpecialEnergy();
        
        controller.playerAttack("punch_strong", false, false, 100);
        
        assertTrue(controller.getPlayer().getSpecialEnergy() > initialEnergy);
    }

    //GAME OVER

    // O jogo acaba quando o jogador morre
    @Test
    void gameShouldEndWhenPlayerDies() {
        // Simula vários ataques do inimigo
        while (controller.getPlayer().isAlive()) {
            controller.enemyAttack(100);
        }

        assertTrue(controller.isGameOver());
        assertEquals("VOCÊ PERDEU!", controller.getResultMessage());
    }

    // O jogo acaba quando o inimigo morre
    @Test
    void gameShouldEndWhenEnemyDies() {
        while (controller.getEnemy().isAlive()) {
            controller.playerAttack("kick_strong", false, false, 100);
        }

        assertTrue(controller.isGameOver());
        assertEquals("VOCÊ VENCEU!!!", controller.getResultMessage());
    }

    //RESET
    
    // Resetar restaura o estado inicial
    @Test
    void resetGameShouldRestoreInitialState() {
        // Danifica primeiro
        controller.playerAttack("kick_strong", false, false, 100);
        controller.enemyAttack(100);

        controller.resetGame();

        assertEquals(100, controller.getPlayer().getHealth());
        assertEquals(100, controller.getEnemy().getHealth());
        assertFalse(controller.isGameOver());
        assertEquals("", controller.getResultMessage());
    }

    //EDGE CASES

    // O ataque não funciona quando o jogo acaba
    @Test
    void attackShouldNotWorkWhenGameIsOver() {
        // Força game over
        while (controller.getEnemy().isAlive()) {
            controller.playerAttack("kick_strong", false, false, 100);
        }

        int enemyHealthBefore = controller.getEnemy().getHealth();

        controller.playerAttack("punch_strong", false, false, 100);

        assertEquals(enemyHealthBefore, controller.getEnemy().getHealth());
    }

}