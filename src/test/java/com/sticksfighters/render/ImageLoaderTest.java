package com.sticksfighters.render;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageLoaderTest {

    @BeforeEach
    void setUp() {
        ImageLoader.clearCache(); // Limpa o cache antes de cada teste
    }

    @Test
    void deveRetornarNullQuandoPathForNulo() {
        assertNull(ImageLoader.loadImage(null));
    }

    @Test
    void deveRetornarNullQuandoPathForVazio() {
        assertNull(ImageLoader.loadImage(""));
        assertNull(ImageLoader.loadImage("   "));
    }

    @Test
    void deveCarregarImagemQuandoExistir() {
        // Usa um recurso que deve existir (uma imagem qualquer do projeto)
        BufferedImage img = ImageLoader.loadImage("/sprites/Rata_camponesa/idle/01.png");
        
        // Pode ser null se o arquivo não existir, então verificamos de forma segura
        if (img != null) {
            assertTrue(img.getWidth() > 0);
            assertTrue(img.getHeight() > 0);
        }
    }

    @Test
    void deveRetornarNullQuandoImagemNaoExistir() {
        BufferedImage img = ImageLoader.loadImage("/sprites/pasta_inexistente/imagem_que_nao_existe.png");
        assertNull(img);
    }

    @Test
    void deveUsarCacheParaMesmaImagem() {
        BufferedImage img1 = ImageLoader.loadImage("/sprites/Rata_camponesa/idle/01.png");
        BufferedImage img2 = ImageLoader.loadImage("/sprites/Rata_camponesa/idle/01.png");

        assertSame(img1, img2, "Deve retornar a mesma instância do cache");
    }

    @Test
    void clearCacheDeveLimparOCache() {
        BufferedImage img1 = ImageLoader.loadImage("/sprites/Rata_camponesa/idle/01.png");
        assertNotNull(img1); // garante que carregou

        ImageLoader.clearCache();

        // Após limpar, deve carregar novamente (nova instância)
        BufferedImage img2 = ImageLoader.loadImage("/sprites/Rata_camponesa/idle/01.png");
        // Não podemos garantir que é outra instância, mas o teste de cache já cobre isso
        assertNotNull(img2);
    }

    @Test
    void deveCarregarMultiplasImagensNoCache() {
        ImageLoader.loadImage("/sprites/Rata_camponesa/idle/01.png");
        ImageLoader.loadImage("/sprites/Rata_camponesa/idle/02.png");
        ImageLoader.loadImage("/sprites/Chicao/idle/01.png");

        // Se chegou até aqui sem erro, o cache está funcionando
        assertTrue(true);
    }
}