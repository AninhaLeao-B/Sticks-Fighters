package com.sticksfighters.render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private static final Map<String, BufferedImage> cache = new HashMap<>();

    public static BufferedImage loadImage(String path) {
        if (path == null || path.trim().isEmpty()) {
            return null;
        }

        // Cache para evitar recarregar a mesma imagem várias vezes
        return cache.computeIfAbsent(path, ImageLoader::loadImageInternal);
    }

    private static BufferedImage loadImageInternal(String path) {
        System.out.println("Carregando imagem: " + path); // pode remover depois

        URL url = ImageLoader.class.getResource(path);
        if (url == null) {
            return null;
        }

        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem " + path);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Limpa o cache (útil para testes ou reset)
     */
    public static void clearCache() {
        cache.clear();
    }
}