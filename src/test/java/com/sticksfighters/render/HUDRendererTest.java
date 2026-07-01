package com.sticksfighters.render;

import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.fighters.Fighters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class HUDRendererTest {

    private Graphics2D graphics;
    private BufferedImage image;
    private Fighter rata;
    private Fighter chicao;

    @BeforeEach
    void setUp() {
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();

        rata = new Fighter(Fighters.rataCamponesa());
        chicao = new Fighter(Fighters.chicao());
    }

    @Test
    void deveDesenharHealthBarSemLancarExcecao() {
        assertDoesNotThrow(() -> {
            HUDRenderer.drawHealthBar(graphics, rata, 50, 50, Color.RED, "Rata Camponesa");
        });
    }

    @Test
    void deveNaoFazerNadaQuandoGraphicsForNulo() {
        assertDoesNotThrow(() -> {
            HUDRenderer.drawHealthBar(null, rata, 50, 50, Color.RED, "Teste");
            HUDRenderer.drawEnergyBar(null, rata, 50, 100);
        });
    }

    @Test
    void deveNaoFazerNadaQuandoFighterForNulo() {
        assertDoesNotThrow(() -> {
            HUDRenderer.drawHealthBar(graphics, null, 50, 50, Color.RED, "Teste");
            HUDRenderer.drawEnergyBar(graphics, null, 50, 100);
        });
    }

    @Test
    void deveCalcularLarguraDaBarraDeVidaCorretamente() {
        // Vida cheia
        assertEquals(200, calcularHealthWidth(rata));

        // Dano de 50%
        rata.receiveDamage(50);
        assertEquals(100, calcularHealthWidth(rata));

        // Vida zerada
        rata.receiveDamage(100);
        assertEquals(0, calcularHealthWidth(rata));
    }

    @Test
    void deveCalcularLarguraDaBarraDeEnergiaCorretamente() {
        // Energia zero
        assertEquals(0, calcularEnergyWidth(rata));

        // Ganha energia
        rata.gainSpecialEnergy(50);
        assertEquals(100, calcularEnergyWidth(rata));

        // Energia máxima
        rata.gainSpecialEnergy(100);
        assertEquals(200, calcularEnergyWidth(rata));
    }

    // Métodos auxiliares para facilitar os testes
    private int calcularHealthWidth(Fighter fighter) {
        int barWidth = 200;
        return (int) (barWidth * fighter.getHealthPercentage() / 100);
    }

    private int calcularEnergyWidth(Fighter fighter) {
        int barWidth = 200;
        return (int) (barWidth * fighter.getSpecialEnergy() / fighter.getMaxSpecialEnergy());
    }
}