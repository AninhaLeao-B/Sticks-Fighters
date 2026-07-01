package com.sticksfighters.render;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SpriteAnimationTest {

    private BufferedImage[] frames3;
    private BufferedImage[] frames5;

    @BeforeEach
    void setUp() {
        frames3 = criarFrames(3);
        frames5 = criarFrames(5);
    }

    private BufferedImage[] criarFrames(int quantidade) {
        BufferedImage[] frames = new BufferedImage[quantidade];
        for (int i = 0; i < quantidade; i++) {
            frames[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        return frames;
    }

    @Test
    void deveCriarAnimacaoComSucesso() {
        SpriteAnimation anim = new SpriteAnimation(frames3, 100);
        assertNotNull(anim);
        assertEquals(3, anim.getTotalFrames());
        assertEquals(0, anim.getCurrentFrameIndex());
    }

    @Test
    void deveLancarExcecaoQuandoFramesNulos() {
        assertThrows(NullPointerException.class, () -> new SpriteAnimation(null, 100));
    }

    @Test
    void deveLancarExcecaoQuandoFramesVazios() {
        assertThrows(IllegalArgumentException.class, 
            () -> new SpriteAnimation(new BufferedImage[0], 100));
    }

    @Test
    void deveAceitarDelayZeroOuNegativo() {
        assertDoesNotThrow(() -> new SpriteAnimation(frames3, 0));
        assertDoesNotThrow(() -> new SpriteAnimation(frames3, -50));
    }

    @Test
    void deveComecarNoPrimeiroFrame() {
        SpriteAnimation anim = new SpriteAnimation(frames5, 100);
        assertEquals(0, anim.getCurrentFrameIndex());
        assertSame(frames5[0], anim.getCurrentFrame());
    }

    @Test
    void deveAvancarFrameAposDelay() throws InterruptedException {
        SpriteAnimation anim = new SpriteAnimation(frames3, 50);

        Thread.sleep(70);
        anim.update();

        assertEquals(1, anim.getCurrentFrameIndex());
    }

    @Test
    void naoDeveAvancarFrameAntesDoDelay() throws InterruptedException {
        SpriteAnimation anim = new SpriteAnimation(frames3, 100);

        Thread.sleep(30);
        anim.update();

        assertEquals(0, anim.getCurrentFrameIndex());
    }

    @Test
    void deveFazerLoopCorretamente() throws InterruptedException {
        SpriteAnimation anim = new SpriteAnimation(frames3, 30);

        for (int i = 0; i < 6; i++) {
            Thread.sleep(40);
            anim.update();
        }

        assertEquals(0, anim.getCurrentFrameIndex()); // deve ter voltado ao início
    }

    @Test
    void deveResetarCorretamente() throws InterruptedException {
        SpriteAnimation anim = new SpriteAnimation(frames5, 100);

        // Avança para o frame 3
        for (int i = 0; i < 3; i++) {
            Thread.sleep(110); // garante que avance
            anim.update();
        }
        assertEquals(3, anim.getCurrentFrameIndex(), "Deveria estar no frame 3 antes do reset");

        // Reseta
        anim.reset();

        // Pequeno delay para garantir que o reset surtiu efeito
        Thread.sleep(20);

        assertEquals(0, anim.getCurrentFrameIndex(), "Após reset deve voltar para o frame 0");
        assertSame(frames5[0], anim.getCurrentFrame(), "Deve retornar o primeiro frame");
    }
    
    @Test
    void resetDeveZerarOTempoParaNaoAvancarImediatamente() throws InterruptedException {
        SpriteAnimation anim = new SpriteAnimation(frames3, 80);

        // Avança um frame
        Thread.sleep(100);
        anim.update();
        assertEquals(1, anim.getCurrentFrameIndex());

        // Reseta
        anim.reset();

        // Espera pouco tempo (menos que o delay)
        Thread.sleep(30);
        anim.update();

        // Não deve ter avançado ainda
        assertEquals(0, anim.getCurrentFrameIndex());
    }

    @Test
    void resetDeveAtualizarOTempo() throws InterruptedException {
        SpriteAnimation anim = new SpriteAnimation(frames3, 100);
        Thread.sleep(150);
        anim.update(); // deve ir para frame 1

        anim.reset();
        Thread.sleep(30); // menos que o delay
        anim.update();

        assertEquals(0, anim.getCurrentFrameIndex()); // não deve ter avançado ainda
    }
}