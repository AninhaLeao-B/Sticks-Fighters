package com.sticksfighters.render;

import com.sticksfighters.animation.CombatAnimationManager;
import com.sticksfighters.fighters.Fighter;
import com.sticksfighters.physics.PlayerMovement;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CharacterRenderer {

    // === Caches de Animação (Tudo mantido igual) ===
    private static final Map<String, SpriteAnimation> idleAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> attackAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> jumpAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> crouchAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> walkAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> damageAnimations = new HashMap<>();
    private static final Map<String, SpriteAnimation> blockAnimations = new HashMap<>();

    private static String lastAction = "";

    // === Métodos getOrLoadAnimation e loadAnimation (Mantidos iguais) ===
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
            return loadAnimation(spriteFolder, "idle");
        }

        int delay = switch (action) {
            case "idle" -> 180;
            case "punch", "kick" -> 60;
            case "walk" -> 80;
            case "jump", "crouch", "damage", "block" -> 90;
            default -> 90;
        };

        return new SpriteAnimation(frames, delay);
    }

    // === Renderização do Player (Ajustado com Lógica de 'Hold') ===
    public static void drawPlayer(Graphics g, PlayerMovement movement,
                                  CombatAnimationManager animations, Fighter fighter) {

        if (g == null || fighter == null || movement == null) return;

        String folder = fighter.getSpriteFolder();
        SpriteAnimation currentAnimation;
        int offsetX = 0;
        int offsetY = 0;
        String currentAction = "idle";

        // === Prioridade de Estados (MANTIDA) ===
        if (animations.isPlayerDamaged()) {
            currentAnimation = getOrLoadAnimation(damageAnimations, folder, "damage");
            currentAction = "damage";
            offsetY = 160;
        }
        else if (animations.isPlayerAttacking()) {
            String attackType = animations.getCurrentAttackType();
            currentAnimation = getOrLoadAnimation(attackAnimations, folder, attackType);
            currentAction = "attack";
            offsetY = 160;
        } 
        else if (fighter.isBlocking()) {
            currentAnimation = getOrLoadAnimation(blockAnimations, folder, "block");
            currentAction = "block";
            offsetY = 160;
        }
        else if (animations.isPlayerJumping() || movement.isJumping()) {
            currentAnimation = getOrLoadAnimation(jumpAnimations, folder, "jump");
            currentAction = "jump";
            offsetY = 160;
        } 
        else if (animations.isPlayerCrouching() || movement.isCrouching()) {
            currentAnimation = getOrLoadAnimation(crouchAnimations, folder, "crouch");
            currentAction = "crouch";
            offsetY = 160;
            offsetX = 10;
        } 
        else if (movement.isMoving()) {
            currentAnimation = getOrLoadAnimation(walkAnimations, folder, "walk");
            currentAction = "walk";
            offsetY = 160;
        } 
        else {
            currentAnimation = getOrLoadAnimation(idleAnimations, folder, "idle");
            currentAction = "idle";
            offsetY = 160;
        }

        // === Reset de Animação ao Mudar de Estado (MANTIDO) ===
        if (!currentAction.equals(lastAction)) {
            currentAnimation.reset();
            lastAction = currentAction;
        }

        // === 🎯 O DETALHE FINAL: Lógica de Parada (Hold) ===
        
        // Se estiver PULANDO, AGACHANDO ou DEFENDENDO
        if (currentAction.equals("crouch") || currentAction.equals("jump") || currentAction.equals("block")) {
            // O ideal é parar no frame central (a pose de guarda ativa).
            // Se sua animação tem 5 frames, ela para no frame index 2 (o 3º frame).
            int holdFrame = currentAnimation.getTotalFrames() / 2;
            
            // SÓ ATUALIZA (muda pro próximo frame) SE AINDA NÃO CHEGOU NO FRAME DE HOLD!
            if (currentAnimation.getCurrentFrameIndex() < holdFrame) {
                currentAnimation.update();
            }
            // Se já chegou no holdFrame, o código NÃO chama o .update(), congelando a animação.
        } else {
            // Outras animações (walk, idle, attack) atualizam normalmente em loop ou fluxo.
            currentAnimation.update();
        }

        // === Desenho da Imagem (MANTIDO) ===
        BufferedImage frame = currentAnimation.getCurrentFrame();
        if (frame != null) {
            g.drawImage(frame,
                        movement.getPlayerX() + offsetX,
                        movement.getPlayerY() - 25 + offsetY,
                        124, 124, null);
        }
    }

    // === Renderização do Inimigo (Ajustado com Lógica de 'Hold') ===
    public static void drawEnemy(Graphics g, int enemyX, CombatAnimationManager animations, Fighter enemy) {
        if (g == null || enemy == null) return;

        String folder = enemy.getSpriteFolder();
        SpriteAnimation currentAnimation;
        int offsetX = 0;
        int offsetY = 160;
        String currentAction = "idle";

        // Prioridades Inimigo
        if (animations.isEnemyDamaged()) {
            currentAnimation = getOrLoadAnimation(damageAnimations, folder, "damage");
            currentAction = "damage";
        }
        else if (animations.isEnemyAttacking()) {
            String attackType = animations.getCurrentAttackType();
            currentAnimation = getOrLoadAnimation(attackAnimations, folder, attackType);
            currentAction = "attack";
        } 
        else if (enemy.isBlocking()) {
            currentAnimation = getOrLoadAnimation(blockAnimations, folder, "block");
            currentAction = "block";
        }
        else {
            currentAnimation = getOrLoadAnimation(idleAnimations, folder, "idle");
            currentAction = "idle";
        }

        // === 🎯 Aplica o Hold também no Inimigo (para combos de teste) ===
        if (currentAction.equals("block")) {
            int holdFrame = currentAnimation.getTotalFrames() / 2;
            if (currentAnimation.getCurrentFrameIndex() < holdFrame) {
                currentAnimation.update();
            }
        } else {
            currentAnimation.update();
        }

        BufferedImage frame = currentAnimation.getCurrentFrame();
        if (frame != null) {
            g.drawImage(frame,
                        enemyX + offsetX,
                        220 - 25 + offsetY,
                        124, 124, null);
        }
    }

    // === Limpeza de Cache (MANTIDO) ===
    public static void clearCache() {
        idleAnimations.clear();
        attackAnimations.clear();
        jumpAnimations.clear();
        crouchAnimations.clear();
        walkAnimations.clear();
        damageAnimations.clear();
        blockAnimations.clear();
        lastAction = "";
    }
}