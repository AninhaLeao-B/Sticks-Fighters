package com.sticksfighters.combat;

import com.sticksfighters.attacks.Attack;
import com.sticksfighters.fighters.Fighter;

public class CombatSystem {

    /**
     * Executa um ataque completo e retorna o dano causado.
     */
	public static void executeAttack(Fighter attacker, Fighter defender, Attack attack) {
        if (attacker == null || defender == null || attack == null) {
            throw new IllegalArgumentException("Attacker, defender and attack cannot be null");
        }
        if (!attacker.isAlive()) {
            return; 
        }

        int finalDamage = attack.getDamage() + attacker.getStrength();
        
        // 🛡️ NOVO: SE ESTIVER DEFENDENDO, REDUZ O DANO!
        if (defender.isBlocking()) {
            double mutiplicadorDano = 1.0 - defender.getDefense(); // Ex: 1.0 - 0.35 = 0.65
            finalDamage = (int) (finalDamage * mutiplicadorDano);
            System.out.println("🛡️ DEFESA DINÂMICA! " + defender.getName() + " absorveu " + (defender.getDefense() * 100) + "% do impacto. Dano sofrido: " + finalDamage);
        }

        defender.receiveDamage(finalDamage);

        if (attack.isSpecial()) {
            attacker.useSpecialEnergy(attack.getEnergyCost());
        } else {
            attacker.gainSpecialEnergy(10); 
        }
    }

    public static int calculateDamage(Fighter attacker, Attack attack) {
        return attack.getDamage() + attacker.getStrength();
    }
    
}