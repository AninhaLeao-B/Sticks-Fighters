package com.sticksfighters.attacks;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AttackTest {
	
	// Garante que o construtor seja criado corretamente
	@Test
	void attackIsCreatedCorrectly() {

	    Attack attack = new Attack(
	        "Soco Forte",
	        20,
	        10,
	        false,
	        "Um soco poderoso"
	    );

	    assertEquals("Soco Forte", attack.getName());
	    assertEquals(20, attack.getDamage());
	    assertEquals(10, attack.getEnergyCost());
	    assertFalse(attack.isSpecial());
	}
	
	// Garante que ataques negativos não sejam tomados
	@Test
	void attackCannotHaveNegativeDamage() {

	    Attack attack = new Attack(
	        "Bug Sombrio",
	        -50,
	        0,
	        false,
	        "Ataque amaldiçoado"
	    );

	    assertEquals(0, attack.getDamage());
	}
	
	// Garante que ataques não podem custar energia negativa
	@Test
	void attackCannotHaveNegativeEnergyCost() {

	    Attack attack = new Attack(
	        "Especial Quebrado",
	        20,
	        -100,
	        true,
	        "Energia impossível"
	    );

	    assertEquals(0, attack.getEnergyCost());
	}
	
	// Garante que o ataque possa ser especial
	@Test
	void attackCanBeSpecial() {

	    Attack attack = new Attack(
	        "Chama Final",
	        50,
	        100,
	        true,
	        "Golpe supremo"
	    );

	    assertTrue(attack.isSpecial());
	}

}