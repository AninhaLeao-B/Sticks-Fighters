package com.sticksfighters.combat;

import com.sticksfighters.attacks.Attack;
import com.sticksfighters.fighters.Fighter;

public class CombatSystem {

    /**
     * Executa um ataque completo: calcula dano, aplica no defensor e gerencia energia.
     */
    public static void executeAttack(Fighter attacker, Fighter defender, Attack attack) {
        if (attacker == null || defender == null || attack == null) {
            throw new IllegalArgumentException("Attacker, defender and attack cannot be null");
        }
        if (!attacker.isAlive()) {
            return; // não ataca se já estiver morto
        }

        int finalDamage = attack.getDamage() + attacker.getStrength();
        defender.receiveDamage(finalDamage);

        if (attack.isSpecial()) {
            attacker.useSpecialEnergy(attack.getEnergyCost());
        } else {
            attacker.gainSpecialEnergy(10); // ganho fixo de energia em ataques normais
        }
    }

    /**
     * Versão mais flexível (caso no futuro queira multiplicadores, críticos, etc.)
     */
    public static int calculateDamage(Fighter attacker, Attack attack) {
        return attack.getDamage() + attacker.getStrength();
    }
}