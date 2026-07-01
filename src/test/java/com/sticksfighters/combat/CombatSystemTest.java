package com.sticksfighters.combat;

import com.sticksfighters.attacks.Attack;
import com.sticksfighters.attacks.AttackFactory;
import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.fighters.Fighters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatSystemTest {

    private Fighter rata;
    private Fighter chicao;

    @BeforeEach
    void setUp() {
        rata = new Fighter(Fighters.rataCamponesa());
        chicao = new Fighter(Fighters.chicao());
    }

    @Test
    void deveReduzirVidaDoDefensor() {
        int vidaInicial = chicao.getHealth();
        Attack attack = AttackFactory.socoFraco();

        CombatSystem.executeAttack(rata, chicao, attack);

        assertTrue(chicao.getHealth() < vidaInicial);
    }

    @Test
    void deveGerarEnergiaEspecialEmAtaqueNormal() {
        int energiaInicial = rata.getSpecialEnergy();
        Attack attack = AttackFactory.socoFraco();

        CombatSystem.executeAttack(rata, chicao, attack);

        assertEquals(energiaInicial + 10, rata.getSpecialEnergy());
    }

    @Test
    void deveConsumirEnergiaEmAtaqueEspecial() {
        rata.gainSpecialEnergy(100);
        Attack hadouken = AttackFactory.hadouken();

        CombatSystem.executeAttack(rata, chicao, hadouken);

        assertEquals(100 - 45, rata.getSpecialEnergy()); // Hadouken custa 45
    }

    @Test
    void deveAplicarDefesaQuandoDefensorEstiverBloqueando() {
        chicao.setBlocking(true);
        int vidaInicial = chicao.getHealth();
        Attack attack = AttackFactory.socoForte(); // dano base 25 + força

        CombatSystem.executeAttack(rata, chicao, attack);

        // Como Chicao tem 0.35 de defense, deve reduzir o dano em 35%
        assertTrue(chicao.getHealth() > vidaInicial - 40); // menos dano que o normal
    }

    @Test
    void naoDeveEntrarEmStunQuandoBloqueando() {
        chicao.setBlocking(true);
        Attack attack = AttackFactory.socoForte();

        CombatSystem.executeAttack(rata, chicao, attack);

        assertFalse(chicao.isStunned());
    }

    @Test
    void deveLancarExcecaoQuandoParametrosNulos() {
        Attack attack = AttackFactory.socoFraco();

        assertThrows(IllegalArgumentException.class, () -> 
            CombatSystem.executeAttack(null, chicao, attack));

        assertThrows(IllegalArgumentException.class, () -> 
            CombatSystem.executeAttack(rata, null, attack));

        assertThrows(IllegalArgumentException.class, () -> 
            CombatSystem.executeAttack(rata, chicao, null));
    }

    @Test
    void naoDeveExecutarAtaqueSeAtacanteEstiverMorto() {
        rata.receiveDamage(999);
        int vidaInicialDefensor = chicao.getHealth();

        CombatSystem.executeAttack(rata, chicao, AttackFactory.socoFraco());

        assertEquals(vidaInicialDefensor, chicao.getHealth()); // não causou dano
    }

    @Test
    void calculateDamageDeveRetornarValorCorreto() {
        Attack attack = AttackFactory.socoForte();

        int dano = CombatSystem.calculateDamage(rata, attack);

        assertEquals(attack.getDamage() + rata.getStrength(), dano);
    }
}