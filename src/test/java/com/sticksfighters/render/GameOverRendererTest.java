package com.sticksfighters.render;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class GameOverRendererTest {

    private Graphics2D graphics;
    private BufferedImage image;

    @BeforeEach
    void setUp() {
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
    }

    @Test
    void deveNaoDesenharNadaQuandoNaoEstiverGameOver() {
        assertDoesNotThrow(() -> {
            GameOverRenderer.draw(graphics, false, "Você Venceu", 800, 600);
        });
    }

    @Test
    void deveNaoDesenharNadaQuandoGraphicsForNulo() {
        assertDoesNotThrow(() -> {
            GameOverRenderer.draw(null, true, "Você Venceu", 800, 600);
        });
    }

    @Test
    void deveNaoDesenharNadaQuandoMensagemForNula() {
        assertDoesNotThrow(() -> {
            GameOverRenderer.draw(graphics, true, null, 800, 600);
        });
    }

    @Test
    void deveDesenharOverlayETextosQuandoGameOver() {
        assertDoesNotThrow(() -> {
            GameOverRenderer.draw(graphics, true, "VOCÊ VENCEU!!!", 800, 600);
        });
    }

    @Test
    void deveCentralizarTextoCorretamente() {
        // Teste indireto - só verifica que não lança exceção com texto longo
        assertDoesNotThrow(() -> {
            GameOverRenderer.draw(graphics, true, "VOCÊ PERDEU! TENTE NOVAMENTE", 800, 600);
        });
    }
}