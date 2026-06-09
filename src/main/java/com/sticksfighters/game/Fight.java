package com.sticksfighters.game;

import com.sticksfighters.attacks.Attack;
import com.sticksfighters.combat.CombatSystem;
import com.sticksfighters.fighters.Fighter;

public class Fight {

    private final Fighter player;
    private final Fighter enemy;
    private int turnNumber;

    public Fight(Fighter player, Fighter enemy) {
        this.player = player;
        this.enemy = enemy;
        this.turnNumber = 1;
    }

    /**
     * Executa um ataque usando o CombatSystem (remove duplicação)
     */
    public void executeAttack(Fighter attacker, Fighter defender, Attack attack) {
        if (attacker == null || defender == null || attack == null) {
            return;
        }

        System.out.println(attacker.getName() + " usou " + attack.getName() + "!");

        if (attack.isSpecial() && !attacker.useSpecialEnergy(attack.getEnergyCost())) {
            System.out.println("Energia insuficiente!");
            return;
        }

        CombatSystem.executeAttack(attacker, defender, attack);

        System.out.println(defender.getName() + " recebeu dano!");
        System.out.println(defender);
    }

    public boolean isOver() {
        return !player.isAlive() || !enemy.isAlive();
    }

    public Fighter getWinner() {
        if (!player.isAlive()) return enemy;
        if (!enemy.isAlive()) return player;
        return null;
    }

    public Fighter getPlayer() { return player; }
    public Fighter getEnemy() { return enemy; }
    public int getTurnNumber() { return turnNumber; }

    public void nextTurn() {
        turnNumber++;
    }

    /**
     * Reseta o combate (útil para múltiplos rounds)
     */
    public void reset() {
        player.reset();
        enemy.reset();
        turnNumber = 1;
    }
}