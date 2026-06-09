package com.sticksfighters.render;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.physics.PlayerMovement;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CharacterRenderer {

    private static final Map<String, SpriteAnimation> idleAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> attackAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> jumpAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> crouchAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> walkAnimations = new HashMap<>();
    
    private static long lastAttackId = -1;

    private static SpriteAnimation getOrLoadAnimation(Map<String, SpriteAnimation> cache,
                                                       String folder, String action) {
        return cache.computeIfAbsent(folder + "_" + action,
            key -> loadAnimation(folder, action));
    }

    private static SpriteAnimation loadAnimation(String spriteFolder, String action) {
        String path = "/sprites/" + spriteFolder + "/" + action;
        BufferedImage[] frames = SpriteLoader.loadFrames(path);

        if (frames.length == 0) {
            System.err.println("⚠ Nenhum frame encontrado para: " + path);
            return loadAnimation(spriteFolder, "idle"); // fallback
        }

        int delay = switch (action) {
            case "idle" -> 180;
            case "punch", "kick" -> 60;
            case "walk" -> 80;
            default -> 90; // jump, crouch
        };

        return new SpriteAnimation(frames, delay);
    }

    public static void drawPlayer(Graphics g, PlayerMovement movement,
                                  CombatAnimationManager animations, Fighter fighter) {

        if (g == null || fighter == null || movement == null) return;

        String folder = fighter.getSpriteFolder();
        SpriteAnimation currentAnimation;
        boolean loopAnimation = true;
        int offsetX = 0;
        int offsetY = 0;

        if (animations.isPlayerAttacking()) {
            currentAnimation =
                    getOrLoadAnimation(
                            attackAnimations,
                            folder,
                            "punch");
            if (animations.getAttackId() != lastAttackId) {

                currentAnimation.reset();

                lastAttackId = animations.getAttackId();
            }

            System.out.println(
                    "Frame ataque: "
                    + currentAnimation.getCurrentFrameIndex());

            loopAnimation = false;
            offsetY = -5;
        }
        else if (animations.isPlayerJumping() || movement.isJumping()) {
            currentAnimation = getOrLoadAnimation(jumpAnimations, folder, "jump");
            loopAnimation = false;
            offsetY = -45;
        } 
        else if (animations.isPlayerCrouching() || movement.isCrouching()) {
            currentAnimation = getOrLoadAnimation(crouchAnimations, folder, "crouch");
            loopAnimation = false;
            offsetY = 0;
            offsetX = 10;
        } 
        else if (movement.isMoving()) {                    // Walk
            currentAnimation = getOrLoadAnimation(walkAnimations, folder, "walk");
            loopAnimation = true;
            offsetY = 0;
        } 
        else {
            currentAnimation = getOrLoadAnimation(idleAnimations, folder, "idle");
            loopAnimation = true;
            offsetY = 0;
        }

        // Controle de looping
        if (!loopAnimation && currentAnimation.getCurrentFrameIndex() >= currentAnimation.getTotalFrames() - 1) {
            // mantém último frame
        } else {
            currentAnimation.update();
        }

        BufferedImage frame = currentAnimation.getCurrentFrame();
        if (frame != null) {
            g.drawImage(frame,
                        movement.getPlayerX() + offsetX,
                        movement.getPlayerY() + 35 + offsetY,
                        124, 124, null);
        }
    }

    public static void drawEnemy(Graphics g, int enemyX, CombatAnimationManager animations) {
        if (g == null) return;
        StickmanRenderer.drawStickman(
                g, enemyX, 220, java.awt.Color.RED,
                false,
                animations.isEnemyAttacking(),
                animations.isEnemyDamaged(),
                false
        );
    }
    
    public static void resetAttackAnimation(String folder) {

        SpriteAnimation attack =
                attackAnimations.get(folder + "_punch");

        if (attack != null) {
            attack.reset();
        }
    }

    public static void clearCache() {
        idleAnimations.clear();
        attackAnimations.clear();
        jumpAnimations.clear();
        crouchAnimations.clear();
        walkAnimations.clear();
    }
}