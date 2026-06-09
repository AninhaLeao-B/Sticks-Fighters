package com.sticksfighters.controller;

import com.sticksfighters.attacks.Attack;
import com.sticksfighters.attacks.AttackFactory;
import com.sticksfighters.combat.CombatSystem;
import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.fighters.Fighters;

import java.util.Objects;
import java.util.Random;

public class GameController {

    private Fighter player;
    private Fighter enemy;
    private boolean gameOver;
    private String resultMessage;

    private final Random random;

    public GameController() {
        this(new Random());
    }
    
    public GameController(FighterData playerData) {
        this(new Random());
        if (playerData != null) {
            this.player = new Fighter(playerData);
        }
    }

    public GameController(Random random) {
        this.random = Objects.requireNonNull(random);
        resetGame();
    }

    public Fighter getPlayer() { return player; }
    public Fighter getEnemy() { return enemy; }
    public boolean isGameOver() { return gameOver; }
    public String getResultMessage() { return resultMessage; }

    public void playerAttack(String type, boolean jumping, boolean crouching, int distance) {
        if (gameOver || !player.isAlive()) return;

        Attack attack = chooseAttack(type, jumping, crouching);
        if (attack == null) {
            System.out.println("Ataque não reconhecido: " + type);
            return;
        }

        if (!canHit(player, distance, jumping, crouching)) {
            System.out.println("Fora de alcance! Distância: " + distance);
            return;
        }

        System.out.println("Player atacou com: " + attack.getName()); // debug

        CombatSystem.executeAttack(player, enemy, attack);
        checkGameOver();
    }

    public void enemyAttack(int distance) {
        if (gameOver || !enemy.isAlive()) return;

        Attack attack = AttackFactory.getRandomNormalAttack(random.nextLong());
        if (!canHit(enemy, distance, false, false)) return;

        CombatSystem.executeAttack(enemy, player, attack);
        checkGameOver();
    }

    private Attack chooseAttack(String type, boolean jumping, boolean crouching) {
        if (type == null) return null;

        if (jumping) {
            if (type.contains("punch") || type.contains("soco")) return AttackFactory.socoAereo();
            if (type.contains("kick") || type.contains("chute")) return AttackFactory.chuteAereo();
        }
        if (crouching) {
            if (type.contains("punch") || type.contains("soco")) return AttackFactory.socoBaixoForte();
            if (type.contains("kick") || type.contains("chute")) return AttackFactory.chuteBaixoForte();
        }

        return switch (type.toLowerCase()) {
            case "punch_weak" -> AttackFactory.socoFraco();
            case "punch_strong" -> AttackFactory.socoForte();
            case "kick_weak" -> AttackFactory.chuteFraco();
            case "kick_strong" -> AttackFactory.chuteForte();
            case "hadouken" -> AttackFactory.hadouken();
            case "shoryuken" -> AttackFactory.shoryuken();
            default -> null;
        };
    }

    private boolean canHit(Fighter attacker, int distance, boolean jumping, boolean crouching) {
        int baseRange = attacker.getRange();

        int hitRange = 95 + baseRange;     // Normal - mais generoso

        if (jumping) {
            hitRange = 125 + baseRange;
        } else if (crouching) {
            hitRange = 80 + baseRange;
        }

        boolean canHit = distance < hitRange;

        if (canHit) {
            System.out.println("✅ ATAQUE LIBERADO! Dist: " + distance + " | HitRange: " + hitRange);
        } else {
            System.out.println("❌ Fora de alcance! Dist: " + distance + " | HitRange: " + hitRange);
        }

        return canHit;
    }

    private void checkGameOver() {
        if (!player.isAlive()) {
            gameOver = true;
            resultMessage = "VOCÊ PERDEU!";
        } else if (!enemy.isAlive()) {
            gameOver = true;
            resultMessage = "VOCÊ VENCEU!!!";
        }
    }

    public void resetGame() {
        // Se quiser manter o mesmo personagem após reset, guarde ele em um campo
        if (player == null) {
            player = new Fighter(Fighters.rataCamponesa());
        } else {
            player = new Fighter(player.getData()); // reset mantendo o mesmo lutador
        }
        enemy = new Fighter(Fighters.chicao());
        gameOver = false;
        resultMessage = "";
    }
}