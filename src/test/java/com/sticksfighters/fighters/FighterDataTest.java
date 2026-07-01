package com.sticksfighters.fighters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FighterDataTest {

    @Test
    void deveCriarFighterDataCorretamente() {
        FighterData data = new FighterData("Rata Camponesa", 100, 22, 10, 0, 0.0, "rata_camponesa");

        assertEquals("Rata Camponesa", data.getName());
        assertEquals(100, data.getMaxHealth());
        assertEquals(22, data.getSpeed());
        assertEquals(10, data.getStrength());
        assertEquals(0, data.getRange());
        assertEquals(0.0, data.getDefense());
        assertEquals("rata_camponesa", data.getSpriteFolder());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData(null, 100, 20, 10, 0, 0.0, "pasta"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData("   ", 100, 20, 10, 0, 0.0, "pasta"));
    }

    @Test
    void deveLancarExcecaoQuandoMaxHealthForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData("Teste", 0, 20, 10, 0, 0.0, "pasta"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData("Teste", -50, 20, 10, 0, 0.0, "pasta"));
    }

    @Test
    void deveLancarExcecaoQuandoValoresNegativos() {
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData("Teste", 100, -1, 10, 0, 0.0, "pasta"));
    }

    @Test
    void deveLancarExcecaoQuandoSpriteFolderForNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData("Teste", 100, 20, 10, 0, 0.0, null));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new FighterData("Teste", 100, 20, 10, 0, 0.0, "   "));
    }

    @Test
    void deveAceitarDefenseNegativaOuZero() {
        // Defesa pode ser negativa ou zero (pode significar fraqueza)
        assertDoesNotThrow(() -> 
            new FighterData("Teste", 100, 20, 10, 0, -5.0, "pasta"));
    }
}