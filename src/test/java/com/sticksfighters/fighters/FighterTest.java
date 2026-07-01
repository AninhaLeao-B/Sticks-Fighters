package com.sticksfighters.fighters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FighterTest {

    private Fighter rata;
    private Fighter chicao;

    @BeforeEach
    void setUp() {
        rata = new Fighter(Fighters.rataCamponesa());
        chicao = new Fighter(Fighters.chicao());
    }

    @Test
    void deveIniciarComVidaMaxima() {
        assertEquals(rata.getMaxHealth(), rata.getHealth());
        assertTrue(rata.isAlive());
        assertEquals(0, rata.getSpecialEnergy());
    }

    @Test
    void deveReceberDanoCorretamente() {
        int vidaInicial = rata.getHealth();
        rata.receiveDamage(25);
        assertEquals(vidaInicial - 25, rata.getHealth());
    }

    @Test
    void naoDeveReceberDanoNegativo() {
        int vidaInicial = rata.getHealth();
        rata.receiveDamage(-30);
        assertEquals(vidaInicial, rata.getHealth());
    }

    @Test
    void deveMorrerQuandoVidaChegarAZero() {
        rata.receiveDamage(rata.getMaxHealth() + 10);
        assertEquals(0, rata.getHealth());
        assertFalse(rata.isAlive());
    }

    @Test
    void deveBloquearDanoSemEntrarEmStun() {
        rata.setBlocking(true);
        rata.receiveDamage(40);

        assertEquals(rata.getMaxHealth() - 40, rata.getHealth()); // ainda perde vida
        assertFalse(rata.isStunned()); // mas não fica stunado
    }

    @Test
    void deveEntrarEmStunQuandoReceberDanoSemBloquear() {
        rata.setBlocking(false);
        rata.receiveDamage(30);
        assertTrue(rata.isStunned());
    }

    @Test
    void stunDeveExpirarDepoisDoTempo() throws InterruptedException {
        rata.receiveDamage(30);
        assertTrue(rata.isStunned());

        Thread.sleep(800); // um pouco mais que os 750ms
        assertFalse(rata.isStunned());
    }

    @Test
    void deveCurarCorretamenteSemExcederMaximo() {
        rata.receiveDamage(40);
        rata.heal(20);
        assertEquals(rata.getMaxHealth() - 20, rata.getHealth());

        rata.heal(100); // tenta curar além do máximo
        assertEquals(rata.getMaxHealth(), rata.getHealth());
    }

    @Test
    void deveGerenciarEnergiaEspecialCorretamente() {
        rata.gainSpecialEnergy(60);
        assertEquals(60, rata.getSpecialEnergy());

        rata.gainSpecialEnergy(100); // tenta ultrapassar
        assertEquals(100, rata.getSpecialEnergy());

        boolean usou = rata.useSpecialEnergy(40);
        assertTrue(usou);
        assertEquals(60, rata.getSpecialEnergy());

        boolean naoUsou = rata.useSpecialEnergy(100);
        assertFalse(naoUsou);
    }

    @Test
    void deveResetarCorretamente() {
        rata.receiveDamage(50);
        rata.gainSpecialEnergy(80);
        rata.setBlocking(true);

        rata.reset();

        assertEquals(rata.getMaxHealth(), rata.getHealth());
        assertEquals(0, rata.getSpecialEnergy());
        assertTrue(rata.isAlive());
        assertFalse(rata.isStunned());
        assertFalse(rata.isBlocking());
    }

    @Test
    void deveCarregarAtributosDoFighterDataCorretamente() {
        assertEquals("Rata Camponesa", rata.getName());
        assertEquals(100, rata.getMaxHealth());
        assertEquals(22, rata.getSpeed());
        assertEquals(10, rata.getStrength());
        assertEquals(0, rata.getRange());

        assertEquals("Chicão", chicao.getName());
        assertEquals(140, chicao.getMaxHealth());
    }

    @Test
    void healthPercentageDeveSerCalculadoCorretamente() {
        assertEquals(100.0, rata.getHealthPercentage(), 0.001);

        rata.receiveDamage(50);
        assertEquals(50.0, rata.getHealthPercentage(), 0.001);

        rata.receiveDamage(100);
        assertEquals(0.0, rata.getHealthPercentage(), 0.001);
    }
}