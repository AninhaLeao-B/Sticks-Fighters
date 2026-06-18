package com.sticksfighters.fighters;

import java.util.ArrayList;
import java.util.List;

public class Fighters {

    public static FighterData rataCamponesa() {
        return new FighterData(
                "Rata Camponesa",
                100,  // life
                22,   // speed
                10,   // strength
                0,    // range
                0.15, // defense
                "rata_camponesa"
        );
    }

    public static FighterData chicao() {
        return new FighterData(
                "Chicão",
                140,  // life
                16,   // speed
                18,   // strength
                15,   // range
                0.35, // defense
                "chicao"
        );
    }
    
    public static FighterData cornoVei() {
        return new FighterData(
            "Corno Vei",
            200,
            10,
            4,
            15,
            0.20,
            "Corno_vei"
        );
    }
    
    public static FighterData reiConde() {
        return new FighterData(
                "Rei Conde",
                150,               // Vida (Mais resistente que o Chicão!)
                14,                // Velocidade (Meio cadenciado, na elegância do brega)
                20,                // Força (Bate com o peso do sofrimento amoroso)
                18,                // Alcance (Os anéis nos dedos dão mais alcance)
                0.30,              // Defesa (Absorve 30% do dano na guarda)
                "Rei_conde"       // Nome exato da pasta de sprites dele
        );
    }

    public static FighterData gogoNyx() {
        return new FighterData(
                "Gogo Nyx",
                110,               // Vida (Mais frágil, estilo assassina/ninja)
                24,                // Velocidade (Mais rápida do jogo, superando a Rata!)
                14,                // Força (Ataques rápidos, mas menos brutos)
                12,                // Alcance (Mão a mão ou adaga invisível)
                0.10,              // Defesa (Bloqueia só 10%, o foco dela é desviar)
                "Gogo_nyx"        // Nome exato da pasta de sprites dela
        );
    }

    /**
     * Retorna todos os lutadores disponíveis (útil para tela de seleção)
     */
    public static List<FighterData> getAllFighters() {
        List<FighterData> fighters = new ArrayList<>();
        fighters.add(rataCamponesa());
        fighters.add(chicao());
        // fighters.add(cornoVei());
        fighters.add(reiConde());
        fighters.add(gogoNyx());
        // Adicione novos aqui facilmente
        return fighters;
    }

    /**
     * Busca por nome (case insensitive)
     */
    public static FighterData getByName(String name) {
        if (name == null) return null;
        
        for (FighterData f : getAllFighters()) {
            if (f.getName().equalsIgnoreCase(name.trim())) {
                return f;
            }
        }
        return null;
    }
}