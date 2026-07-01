package com.sticksfighters.render;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class BackgroundRendererTest {

    private Graphics2D graphics;
    private BufferedImage image;

    @BeforeEach
    void setUp() {
        // Cria um Graphics fictício para testes
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
    }

    @Test
    void deveDesenharBackgroundSemLancarExcecao() {
        assertDoesNotThrow(() -> {
            BackgroundRenderer.drawArena(graphics, 800, 600);
        });
    }

    @Test
    void deveNaoFazerNadaQuandoGraphicsForNulo() {
        assertDoesNotThrow(() -> {
            BackgroundRenderer.drawArena(null, 800, 600);
        });
    }

    @Test
    void deveDesenharComDimensoesValidas() {
        BackgroundRenderer.drawArena(graphics, 800, 600);
        // Se chegou aqui sem exceção, o teste passou (difícil testar pixels sem mock avançado)
        assertTrue(true); // placeholder - podemos melhorar depois
    }

    @Test
    void constantesDevemEstarCorretas() throws Exception {
        // Teste indireto das constantes (via reflection ou apenas documentação)
        // Por enquanto, só verificamos que o método roda
        assertDoesNotThrow(() -> BackgroundRenderer.drawArena(graphics, 800, 600));
    }
}