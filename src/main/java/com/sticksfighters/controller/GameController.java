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
        this(null, new Random());
    }

    public GameController(FighterData playerData) {
        this(playerData, new Random());
    }

    // Construtor principal corrigido: garante que playerData seja processado antes do reset
    public GameController(FighterData playerData, Random random) {
        this.random = Objects.requireNonNull(random);
        if (playerData != null) {
            this.player = new Fighter(playerData);
        }
        resetGame(); 
    }

    public Fighter getPlayer() { return player; }
    public Fighter getEnemy() { return enemy; }
    public boolean isGameOver() { return gameOver; }
    public String getResultMessage() { return resultMessage; }

    public boolean playerAttack(String type, boolean jumping, boolean crouching, int distance) {
        if (gameOver || !player.isAlive() || player.isStunned()) return false;

        Attack attack = chooseAttack(type, jumping, crouching);
        if (attack == null) return false;

        if (!canHit(player, distance, jumping, crouching)) return false;

        CombatSystem.executeAttack(player, enemy, attack);
        checkGameOver();
        return true; 
    }

    public boolean enemyAttack(int distance, boolean playerJumping, boolean playerCrouching) {
        if (gameOver || !enemy.isAlive()) return false; 

        Attack attack = AttackFactory.getRandomNormalAttack(random.nextLong());
        
        // O contra-ataque do inimigo calcula se alcança o player em pé
        if (!canHit(enemy, distance, false, false)) return false; 

        CombatSystem.executeAttack(enemy, player, attack);
        checkGameOver();
        return true;
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
        
        // Ajustado para 140 para cobrir perfeitamente as caixas de colisão
        int hitRange = 140 + baseRange; 

        if (jumping) hitRange = 170 + baseRange;
        else if (crouching) hitRange = 120 + baseRange;

        return distance < hitRange;
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
        // Mantém o jogador atual ou cria a Rata
        if (player == null) {
            player = new Fighter(Fighters.rataCamponesa());
        } else {
            player = new Fighter(player.getData()); 
        }

        enemy = new Fighter(Fighters.cornoVei());

        gameOver = false;
        resultMessage = "";
    }
}