package com.sticksfighters.attacks;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AttackFactoryTest {
	
	// Garante que o soco fraco tem o dano correto
	@Test
	void weakPunchHasCorrectDamage() {

	    Attack attack = AttackFactory.socoFraco();

	    assertEquals(14, attack.getDamage());
	}
	
	// garante que o soco forte tem o dano correto
	@Test
	void strongPunchHasCorrectDamage() {
		
		Attack attack = AttackFactory.socoForte();
		
		assertEquals(24, attack.getDamage());
	}
	
	// garante que o chute fraco tem o dano correto
	@Test
	void weakKickHasCorrectDamage() {
		
		Attack attack = AttackFactory.chuteFraco();
		
		assertEquals(16, attack.getDamage());
	}
	
	// garante que o chute forte tem o dano correto
	@Test
	void strongKickHasCorrectDamage() {
		
		Attack attack = AttackFactory.chuteForte();
		
		assertEquals(30, attack.getDamage());
	}
	
	// Garante que o hadouken é especial
	@Test
	void hadoukenIsSpecialAttack() {

	    Attack attack = AttackFactory.hadouken();

	    assertTrue(attack.isSpecial());
	}
	
	// Garante que o ataque aleatório nunca retorne null
	@Test
	void randomAttackShouldNeverBeNull() {

	    Attack attack = AttackFactory.getRandomNormalAttack();

	    assertNotNull(attack);
	}

}
