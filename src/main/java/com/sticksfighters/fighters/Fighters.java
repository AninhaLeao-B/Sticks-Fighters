package com.sticksfighters.fighters;

import java.util.ArrayList;
import java.util.List;

public class Fighters {

    public static FighterData rataCamponesa() {
        return new FighterData(
                "Rata Camponesa",
                100,
                22,   // speed
                10,   // strength
                0,    // range
                "rata_camponesa"
        );
    }

    public static FighterData chicao() {
        return new FighterData(
                "Chicão",
                140,
                16,   // speed
                18,   // strength
                15,   // range
                "chicao"
        );
    }

    /**
     * Retorna todos os lutadores disponíveis (útil para tela de seleção)
     */
    public static List<FighterData> getAllFighters() {
        List<FighterData> fighters = new ArrayList<>();
        fighters.add(rataCamponesa());
        fighters.add(chicao());
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