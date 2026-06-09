package com.sticksfighters.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteLoader {

    /**
     * Carrega uma sequência de frames numerados (01.png, 02.png, etc.)
     */
    public static BufferedImage[] loadFrames(String folder, int count) {
        if (folder == null || count <= 0) {
            return new BufferedImage[0];
        }

        List<BufferedImage> frames = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String number = String.format("%02d", i);
            String path = folder + "/" + number + ".png";

            BufferedImage img = ImageLoader.loadImage(path);

            if (img != null) {
                frames.add(img);
            } else {
                System.err.println("⚠ Frame não encontrado: " + path);
            }
        }

        return frames.toArray(new BufferedImage[0]);
    }

    /**
     * Versão flexível caso no futuro use outro padrão de nome de arquivo
     */
    public static BufferedImage[] loadFrames(String basePath, String extension, int count) {
        // pode ser expandido depois
        return loadFrames(basePath, count);
    }
    
    public static BufferedImage[] loadFrames(
            String folder) {

        if (folder == null) {
            return new BufferedImage[0];
        }

        List<BufferedImage> frames =
                new ArrayList<>();

        int index = 1;

        while (true) {

            String number =
                    String.format("%02d", index);

            String path =
                    folder + "/" + number + ".png";

            BufferedImage img =
                    ImageLoader.loadImage(path);

            if (img == null) {
                break;
            }

            frames.add(img);

            index++;
        }

        return frames.toArray(
                new BufferedImage[0]);
    }
}