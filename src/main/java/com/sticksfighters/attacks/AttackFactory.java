package com.sticksfighters.attacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AttackFactory {

    private static final Random RANDOM = new Random();

    // ==================== SOCOS ====================
    public static Attack socoBaixoFraco() {
        return new Attack("Soco Baixo Fraco", 10, 0, false, "Soco baixo rápido");
    }

    public static Attack socoBaixoForte() {
        return new Attack("Soco Baixo Forte", 18, 12, false, "Soco baixo potente");
    }

    public static Attack socoFraco() {
        return new Attack("Soco Fraco", 14, 5, false, "Soco normal rápido");
    }

    public static Attack socoForte() {
        return new Attack("Soco Forte", 24, 18, false, "Soco normal poderoso");
    }

    public static Attack socoAereo() {
        return new Attack("Soco Aéreo", 20, 15, false, "Soco dado no ar");
    }

    // ==================== CHUTES ====================
    public static Attack chuteBaixoFraco() {
        return new Attack("Chute Baixo Fraco", 13, 8, false, "Chute baixo rápido");
    }

    public static Attack chuteBaixoForte() {
        return new Attack("Chute Baixo Forte", 26, 22, false, "Chute baixo varrido");
    }

    public static Attack chuteFraco() {
        return new Attack("Chute Fraco", 16, 10, false, "Chute normal");
    }

    public static Attack chuteForte() {
        return new Attack("Chute Forte", 30, 25, false, "Chute alto poderoso");
    }

    public static Attack chuteAereo() {
        return new Attack("Chute Aéreo", 28, 20, false, "Chute giratório no ar");
    }

    // ==================== ESPECIAIS ====================
    public static Attack hadouken() {
        return new Attack("Hadouken", 38, 45, true, "Bola de energia");
    }

    public static Attack shoryuken() {
        return new Attack("Shoryuken", 42, 50, true, "Uppercut flamejante");
    }

    /**
     * Ataque aleatório para IA (versão testável)
     */
    public static Attack getRandomNormalAttack() {
        Attack[] attacks = {
            socoFraco(), socoForte(), chuteFraco(), chuteForte(),
            socoBaixoForte(), chuteBaixoForte(), chuteAereo()
        };
        return attacks[RANDOM.nextInt(attacks.length)];
    }

    /**
     * Versão determinística - ótima para testes
     */
    public static Attack getRandomNormalAttack(long seed) {
        Random seededRandom = new Random(seed);
        Attack[] attacks = {
            socoFraco(), socoForte(), chuteFraco(), chuteForte(),
            socoBaixoForte(), chuteBaixoForte(), chuteAereo()
        };
        return attacks[seededRandom.nextInt(attacks.length)];
    }

    /**
     * Retorna todos os ataques normais (útil para testes e UI)
     */
    public static List<Attack> getAllNormalAttacks() {
        List<Attack> list = new ArrayList<>();
        list.add(socoBaixoFraco());
        list.add(socoBaixoForte());
        list.add(socoFraco());
        list.add(socoForte());
        list.add(socoAereo());
        list.add(chuteBaixoFraco());
        list.add(chuteBaixoForte());
        list.add(chuteFraco());
        list.add(chuteForte());
        list.add(chuteAereo());
        return list;
    }
}