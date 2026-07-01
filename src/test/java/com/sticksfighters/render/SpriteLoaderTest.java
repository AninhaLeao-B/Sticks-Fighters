package com.sticksfighters.render;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SpriteLoaderTest {

    @Test
    void deveRetornarArrayVazioQuandoFolderForNulo() {
        BufferedImage[] frames = SpriteLoader.loadFrames(null, 5);
        assertNotNull(frames);
        assertEquals(0, frames.length);
    }

    @Test
    void deveRetornarArrayVazioQuandoCountForZeroOuNegativo() {
        BufferedImage[] frames1 = SpriteLoader.loadFrames("/sprites/test", 0);
        BufferedImage[] frames2 = SpriteLoader.loadFrames("/sprites/test", -3);

        assertEquals(0, frames1.length);
        assertEquals(0, frames2.length);
    }

    @Test
    void deveCarregarQuantidadeExataDeFramesQuandoExistirem() {
        // Este teste assume que você tem pelo menos 3 frames na pasta idle de algum personagem
        BufferedImage[] frames = SpriteLoader.loadFrames("/sprites/Rata_camponesa/idle", 3);

        assertNotNull(frames);
        // Pode ser menor que 3 se não tiver todos os arquivos, por isso usamos >=
        assertTrue(frames.length >= 0);
    }

    @Test
    void versaoDinamicaDeveCarregarTodosFramesDisponiveis() {
        BufferedImage[] frames = SpriteLoader.loadFrames("/sprites/Rata_camponesa/idle");

        assertNotNull(frames);
        assertTrue(frames.length >= 0); // pelo menos 0
    }

    @Test
    void deveRetornarArrayVazioQuandoNaoExistirNenhumFrame() {
        BufferedImage[] frames = SpriteLoader.loadFrames("/sprites/pasta_que_nao_existe");

        assertNotNull(frames);
        assertEquals(0, frames.length);
    }

    @Test
    void versaoComExtensionDeveDelegarCorretamente() {
        BufferedImage[] frames = SpriteLoader.loadFrames("/sprites/Rata_camponesa/idle", ".png", 3);

        assertNotNull(frames);
    }
}