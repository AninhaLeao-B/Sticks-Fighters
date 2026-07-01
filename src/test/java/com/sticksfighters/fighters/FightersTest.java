package com.sticksfighters.fighters;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FightersTest {

    @Test
    void deveCriarRataCamponesaCorretamente() {
        FighterData rata = Fighters.rataCamponesa();

        assertEquals("Rata Camponesa", rata.getName());
        assertEquals(100, rata.getMaxHealth());
        assertEquals(22, rata.getSpeed());
        assertEquals(10, rata.getStrength());
        assertEquals(0, rata.getRange());
        assertEquals(0.15, rata.getDefense(), 0.001);
        assertEquals("rata_camponesa", rata.getSpriteFolder());
    }

    @Test
    void deveCriarChicaoCorretamente() {
        FighterData chicao = Fighters.chicao();

        assertEquals("Chicão", chicao.getName());
        assertEquals(140, chicao.getMaxHealth());
        assertEquals(16, chicao.getSpeed());
        assertEquals(18, chicao.getStrength());
        assertEquals(15, chicao.getRange());
        assertEquals(0.35, chicao.getDefense(), 0.001);
    }

    @Test
    void deveRetornarListaComTodosLutadores() {
        List<FighterData> fighters = Fighters.getAllFighters();

        assertNotNull(fighters);
        assertTrue(fighters.size() >= 4); // Rata, Chicão, Rei Conde, Gogo Nyx

        // Verifica se contém os principais
        assertTrue(fighters.stream().anyMatch(f -> f.getName().equals("Rata Camponesa")));
        assertTrue(fighters.stream().anyMatch(f -> f.getName().equals("Chicão")));
        assertTrue(fighters.stream().anyMatch(f -> f.getName().equals("Rei Conde")));
        assertTrue(fighters.stream().anyMatch(f -> f.getName().equals("Gogo Nyx")));
    }

    @Test
    void deveBuscarLutadorPorNomeCaseInsensitive() {
        FighterData rata = Fighters.getByName("rata camponesa");
        FighterData chicao = Fighters.getByName("CHICÃO");
        FighterData rei = Fighters.getByName("rei conde");

        assertNotNull(rata);
        assertNotNull(chicao);
        assertNotNull(rei);

        assertEquals("Rata Camponesa", rata.getName());
        assertEquals("Chicão", chicao.getName());
    }

    @Test
    void deveRetornarNullQuandoNomeNaoExistir() {
        assertNull(Fighters.getByName("Personagem Que Não Existe"));
    }

    @Test
    void deveRetornarNullQuandoNomeForNulo() {
        assertNull(Fighters.getByName(null));
    }
}