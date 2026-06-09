package com.sticksfighters.render;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class SpriteAnimation {

    private final BufferedImage[] frames;
    private final int frameDelay;

    private int currentFrame = 0;
    private long lastFrameTime;

    public SpriteAnimation(BufferedImage[] frames, int frameDelay) {
        this.frames = Objects.requireNonNull(frames, "Frames cannot be null");
        if (frames.length == 0) {
            throw new IllegalArgumentException("Frames array cannot be empty");
        }
        this.frameDelay = Math.max(1, frameDelay); // evita delay zero ou negativo
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime > frameDelay) {
            currentFrame = (currentFrame + 1) % frames.length;
            lastFrameTime = currentTime;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    public void reset() {
        currentFrame = 0;
        lastFrameTime = System.currentTimeMillis();
        
        System.out.println("RESETANDO ANIMAÇÃO");
    }

    // Útil para testes
    public int getCurrentFrameIndex() {
        return currentFrame;
    }

    public int getTotalFrames() {
        return frames.length;
    }
}