package com.sticksfighters.combat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.sticksfighters.attacks.Attack;
import com.sticksfighters.attacks.AttackFactory;
import com.sticksfighters.controller.GameController;
import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.fighters.Fighters;

public class CombatSystemTest {
	
	@Test
	void attackShouldReduceEnemyHealth() {

		Fighter attacker =
		        new Fighter(Fighters.rataCamponesa());

		Fighter defender =
		        new Fighter(Fighters.chicao());

	    Attack attack = AttackFactory.socoFraco();

	    CombatSystem.executeAttack(attacker, defender, attack);

	    assertEquals(116, defender.getHealth());
	}
	
	@Test
	void attackShouldGenerateSpecialEnergy() {

		Fighter attacker =
		        new Fighter(Fighters.rataCamponesa());

		Fighter defender =
		        new Fighter(Fighters.chicao());

	    Attack attack = AttackFactory.socoFraco();

	    CombatSystem.executeAttack(attacker, defender, attack);

	    assertEquals(10, attacker.getSpecialEnergy());
	}
	
	@Test
	void specialAttackShouldConsumeEnergy() {

		Fighter attacker =
		        new Fighter(Fighters.rataCamponesa());

		Fighter defender =
		        new Fighter(Fighters.chicao());

	    attacker.gainSpecialEnergy(100);

	    Attack attack = AttackFactory.hadouken();

	    CombatSystem.executeAttack(attacker, defender, attack);

	    assertEquals(55, attacker.getSpecialEnergy());
	}
	
	@Test
	void rataShouldNotHitFromFarAway() {

	    GameController controller =
	            new GameController();

	    int initialHealth =
	            controller.getEnemy().getHealth();

	    controller.playerAttack(
	            "punch_weak",
	            false,
	            false,
	            250);

	    assertEquals(
	            initialHealth,
	            controller.getEnemy().getHealth());
	}
	
	@Test
	void rataShouldHitFromCloseRange() {

	    GameController controller =
	            new GameController();

	    int initialHealth =
	            controller.getEnemy().getHealth();

	    controller.playerAttack(
	            "punch_weak",
	            false,
	            false,
	            50);

	    assertTrue(
	            controller.getEnemy().getHealth()
	                    < initialHealth);
	}
	
	@Test
	void rataShouldHaveZeroExtraRange() {

	    Fighter rata =
	            new Fighter(
	                    Fighters.rataCamponesa());

	    assertEquals(
	            0,
	            rata.getRange());
	}
	
	@Test
	void chicaoShouldHaveExtraRange() {

	    Fighter chicao =
	            new Fighter(
	                    Fighters.chicao());

	    assertEquals(
	            15,
	            chicao.getRange());
	}

}
