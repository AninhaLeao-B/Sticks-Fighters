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

    private static String lastAction = "";

    private static SpriteAnimation getOrLoadAnimation(Map<String, SpriteAnimation> cache,
                                                       String folder, String action) {
        return cache.computeIfAbsent(folder + "_" + action,
            key -> loadAnimation(folder, action));
    }

    private static SpriteAnimation loadAnimation(String spriteFolder, String action) {
        String path = "/sprites/" + spriteFolder + "/" + action;
        
        // Carrega TODOS os frames disponíveis (sem limite fixo)
        BufferedImage[] frames = SpriteLoader.loadFrames(path);   // sem passar quantidade

        if (frames.length == 0) {
            System.err.println("⚠ Nenhum frame encontrado para: " + path);
            return loadAnimation(spriteFolder, "idle"); // fallback
        }

        int delay = switch (action) {
            case "idle" -> 180;
            case "punch", "kick" -> 60;
            case "walk" -> 80;
            case "jump", "crouch" -> 90;
            default -> 90;
        };

        return new SpriteAnimation(frames, delay);
    }

    public static void drawPlayer(Graphics g, PlayerMovement movement,
            CombatAnimationManager animations, Fighter fighter) {

		if (g == null || fighter == null || movement == null) return;
		
		String folder = fighter.getSpriteFolder();
		SpriteAnimation currentAnimation;
		int offsetX = 0;
		int offsetY = 0;          // Vamos calibrar bem aqui
		String currentAction = "idle";
		
		if (animations.isPlayerAttacking()) {
            String attackType = animations.getCurrentAttackType(); // "punch" ou "kick"
            currentAnimation = getOrLoadAnimation(attackAnimations, folder, attackType);
            currentAction = "attack";
            offsetY = 60;   // mantenha seu valor
        } 
		else if (animations.isPlayerJumping() || movement.isJumping()) {
			currentAnimation = getOrLoadAnimation(jumpAnimations, folder, "jump");
			currentAction = "jump";
			offsetY = 60;        // pulo precisa subir bastante
		} 
		else if (animations.isPlayerCrouching() || movement.isCrouching()) {
		currentAnimation = getOrLoadAnimation(crouchAnimations, folder, "crouch");
		currentAction = "crouch";
			offsetY = 60;         // agachado desce
			offsetX = 8;
		} 
		else if (movement.isMoving() && !movement.isJumping() && !movement.isCrouching()) {
			currentAnimation = getOrLoadAnimation(walkAnimations, folder, "walk");
			currentAction = "walk";
			offsetY = 65;         // walk geralmente fica um pouco mais baixo
		} 
		else {
			currentAnimation = getOrLoadAnimation(idleAnimations, folder, "idle");
			currentAction = "idle";
			offsetY = 60;         // idle como referência principal
		}
		
		// Reset quando muda de animação
		if (!currentAction.equals(lastAction)) {
			currentAnimation.reset();
			lastAction = currentAction;
		}
		
		// Lógica de atualização
		if (currentAction.equals("crouch") || currentAction.equals("jump")) {
			int middleFrame = currentAnimation.getTotalFrames() / 2;
		if (currentAnimation.getCurrentFrameIndex() < middleFrame) {
			currentAnimation.update();
			}
		} else {
			currentAnimation.update();
		}
		
		BufferedImage frame = currentAnimation.getCurrentFrame();
		if (frame != null) {
			g.drawImage(frame,
			  movement.getPlayerX() + offsetX,
			  movement.getPlayerY() - 25 + offsetY,   // base aqui
			  124, 124, null);
			}
		}

    // ... drawEnemy e clearCache permanecem iguais ...
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

    public static void clearCache() {
        idleAnimations.clear();
        attackAnimations.clear();
        jumpAnimations.clear();
        crouchAnimations.clear();
        walkAnimations.clear();
        lastAction = "";
    }
}
