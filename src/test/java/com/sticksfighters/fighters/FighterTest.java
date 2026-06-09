package com.sticksfighters.fighters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FighterTest {

	// Garante que o construtor funciona
	@Test
	void fighterStartsWithMaxHealth() {

	    Fighter fighter =
	            new Fighter(Fighters.rataCamponesa());

	    assertEquals(
	            fighter.getMaxHealth(),
	            fighter.getHealth());
	}
    
    // Garante o recebimento de dano
	@Test
	void fighterLosesHealthWhenReceivingDamage() {

	    Fighter fighter =
	            new Fighter(Fighters.rataCamponesa());

	    int initialHealth =
	            fighter.getHealth();

	    fighter.receiveDamage(20);

	    assertEquals(
	            initialHealth - 20,
	            fighter.getHealth());
	}
    
    // Garante a morte do lutador
    @Test
    void fighterDiesWhenHealthReachesZero() {

    	Fighter fighter =
    	        new Fighter(Fighters.rataCamponesa());

    	fighter.receiveDamage(
    	        fighter.getMaxHealth());

        assertFalse(fighter.isAlive());
    }
    
    // Garante que a energia não pode exceder a capacidade máxima
    @Test
    void specialEnergyCannotExceedMaximum() {

        Fighter fighter =
                new Fighter(Fighters.rataCamponesa());

        fighter.gainSpecialEnergy(200);

        assertEquals(
                fighter.getMaxSpecialEnergy(),
                fighter.getSpecialEnergy());
    }
    
    // Garante que o lutador não pode receber dano negativo
    @Test
    void fighterDoesNotReceiveNegativeDamage() {

        Fighter fighter =
                new Fighter(Fighters.rataCamponesa());

        int initialHealth =
                fighter.getHealth();

        fighter.receiveDamage(-50);

        assertEquals(
                initialHealth,
                fighter.getHealth());
    }
    
    // Garante que o especial não pode ser usado sem energia suficiente
    @Test
    void specialCannotBeUsedWithoutEnoughEnergy() {

    	Fighter fighter =
    	        new Fighter(Fighters.rataCamponesa());

        boolean result = fighter.useSpecialEnergy(50);

        assertFalse(result);
    }
    
    // Garante que a cura não exceda o limite máximo
    @Test
    void healingCannotExceedMaximumHealth() {

        Fighter fighter =
                new Fighter(Fighters.rataCamponesa());

        fighter.receiveDamage(20);
        fighter.heal(50);

        assertEquals(
                fighter.getMaxHealth(),
                fighter.getHealth());
    }
    
    // Lutador carrega os dados corretamente
    @Test
    void fighterShouldLoadDataCorrectly() {

        Fighter fighter =
                new Fighter(Fighters.chicao());

        assertEquals("Chicão", fighter.getName());
        assertEquals(140, fighter.getMaxHealth());
        assertEquals(16, fighter.getSpeed());
        assertEquals(18, fighter.getStrength());
        assertEquals(15, fighter.getRange());
    }
    
    @Test
    void rataShouldHaveCorrectAttributes() {

        Fighter fighter =
                new Fighter(Fighters.rataCamponesa());

        assertEquals("Rata Camponesa", fighter.getName());
        assertEquals(100, fighter.getMaxHealth());
        assertEquals(22, fighter.getSpeed());
        assertEquals(10, fighter.getStrength());
        assertEquals(0, fighter.getRange());
    }
    
    @Test
    void specialEnergyShouldBeConsumed() {

        Fighter fighter =
                new Fighter(Fighters.rataCamponesa());

        fighter.gainSpecialEnergy(100);

        fighter.useSpecialEnergy(50);

        assertEquals(
                50,
                fighter.getSpecialEnergy());
    }
    
    @Test
    void healthShouldNeverBecomeNegative() {

        Fighter fighter =
                new Fighter(Fighters.rataCamponesa());

        fighter.receiveDamage(9999);

        assertEquals(0, fighter.getHealth());
    }
}