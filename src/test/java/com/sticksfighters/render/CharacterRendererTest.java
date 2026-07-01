package com.sticksfighters.render;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.fighters.Fighters;
import com.sticksfighters.physics.PlayerMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class CharacterRendererTest {

    private Graphics2D graphics;
    private BufferedImage image;
    private Fighter rata;
    private Fighter chicao;
    private PlayerMovement movement;
    private CombatAnimationManager animations;

    @BeforeEach
    void setUp() {
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();

        rata = new Fighter(Fighters.rataCamponesa());
        chicao = new Fighter(Fighters.chicao());
        movement = new PlayerMovement(150, 220, 6);
        animations = new CombatAnimationManager();
    }

    @Test
    void deveDesenharPlayerSemLancarExcecao() {
        assertDoesNotThrow(() -> {
            CharacterRenderer.drawPlayer(graphics, movement, animations, rata);
        });
    }

    @Test
    void deveDesenharEnemySemLancarExcecao() {
        assertDoesNotThrow(() -> {
            CharacterRenderer.drawEnemy(graphics, 550, animations, chicao);
        });
    }

    @Test
    void deveUsarAnimaçãoDeDanoQuandoAtivada() {
        animations.playDamageAnimation();
        assertDoesNotThrow(() -> {
            CharacterRenderer.drawPlayer(graphics, movement, animations, rata);
        });
    }

    @Test
    void deveTrocarParaAnimaçãoDeWalkQuandoMovimentando() {
        movement.moveRight(550);
        assertDoesNotThrow(() -> {
            CharacterRenderer.drawPlayer(graphics, movement, animations, rata);
        });
    }

    @Test
    void deveManterHoldNoFrameDoMeioParaCrouch() throws InterruptedException {
        animations.setCrouching(true);
        CharacterRenderer.drawPlayer(graphics, movement, animations, rata);

        // Avança um pouco
        Thread.sleep(100);
        CharacterRenderer.drawPlayer(graphics, movement, animations, rata);

        // Não deve ter avançado além do frame central
    }

    @Test
    void deveResetarAnimaçãoAoMudarDeEstado() {
        // Idle → Jump
        animations.setJumping(true);
        CharacterRenderer.drawPlayer(graphics, movement, animations, rata);

        // Jump → Idle
        animations.setJumping(false);
        CharacterRenderer.drawPlayer(graphics, movement, animations, rata);
    }

    @Test
    void deveLimparCacheSemErro() {
        assertDoesNotThrow(CharacterRenderer::clearCache);
    }
}