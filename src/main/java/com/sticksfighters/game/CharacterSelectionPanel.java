package com.sticksfighters.game;

import com.sticksfighters.fighters.FighterData;
import com.sticksfighters.fighters.Fighters;
import com.sticksfighters.render.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class CharacterSelectionPanel extends JPanel {

    private final List<FighterData> fighters;
    private int selectedIndex = 0;
    private final Runnable onCharacterSelected;
    private FighterData selectedFighter;

    // Controle de animação simples para o personagem grande respirar na tela de seleção
    private int animationFrame = 0;
    private int animationTick = 0;

    public CharacterSelectionPanel(Runnable onCharacterSelected) {
        this.fighters = Fighters.getAllFighters();
        this.onCharacterSelected = onCharacterSelected;

        setBackground(new Color(15, 15, 30)); // Fundo um pouco mais escuro estilo Arcade
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> selectedIndex = (selectedIndex - 1 + fighters.size()) % fighters.size();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> selectedIndex = (selectedIndex + 1) % fighters.size();
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> startGame();
                }
                animationFrame = 0; // Reseta o frame ao mudar para o boneco não herdar o tempo do outro
                repaint();
            }
        });

        // Timer para animar o personagem em destaque na tela de seleção!
        Timer animTimer = new Timer(150, e -> {
            animationTick++;
            if (animationTick >= 1) { // Controla a velocidade do frame
                animationFrame++;
                animationTick = 0;
                repaint();
            }
        });
        animTimer.start();
    }

    private void startGame() {
        selectedFighter = fighters.get(selectedIndex);
        System.out.println("✅ Selecionado: " + selectedFighter.getName());
        onCharacterSelected.run();
    }

    public FighterData getSelectedFighter() {
        return selectedFighter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FighterData currentFighter = fighters.get(selectedIndex);

        // ==================== 1. DESIGN DO TOPO ====================
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 36f));
        String title = "SELECT YOUR FIGHTER";
        g.drawString(title, getWidth() / 2 - g.getFontMetrics().stringWidth(title) / 2, 60);

        // ==================== 2. LADO ESQUERDO: PEEK DO PERSONAGEM EM PÉ ====================
        // Desenha a silhueta ou caixa de preview do lutador grande
        g.setColor(new Color(30, 30, 50, 200));
        g.fillRect(50, 120, 240, 340);
        g.setColor(Color.CYAN);
        g.drawRect(50, 120, 240, 340);

        // Tenta rodar a animação IDLE real do personagem selecionado em tamanho grande!
        String animFrameStr = String.format("%02d.png", (animationFrame % 3) + 1); // Alterna entre 01, 02, 03.png
        String bigSpritePath = "/sprites/" + currentFighter.getSpriteFolder() + "/idle/" + animFrameStr;
        Image bigImg = ImageLoader.loadImage(bigSpritePath);
        
        // Se não achar o frame animado, tenta o estágico 01.png
        if (bigImg == null) {
            bigSpritePath = "/sprites/" + currentFighter.getSpriteFolder() + "/idle/01.png";
            bigImg = ImageLoader.loadImage(bigSpritePath);
        }

        if (bigImg != null) {
            // Desenha o boneco grandão na esquerda
            g.drawImage(bigImg, 50 + 10, 120 + 40, 220, 220, null);
        }

        // Nome grande embaixo do preview do boneco
        g.setColor(Color.YELLOW);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 28f));
        g.drawString(currentFighter.getName(), 50 + 120 - g.getFontMetrics().stringWidth(currentFighter.getName())/2, 430);


        // ==================== 3. CENTRO: A GRADE ARCADE (GRID) ====================
        int gridX = 330;
        int gridY = 180;
        int iconSize = 70; // Tamanho do quadradinho estilo arcade
        int iconGap = 15;  // Espaço entre os quadradinhos

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 16f));
        g.drawString("SELECT GRID:", gridX, gridY - 15);

        for (int i = 0; i < fighters.size(); i++) {
            FighterData f = fighters.get(i);
            int x = gridX + i * (iconSize + iconGap);

            // Se for o quadradinho selecionado, brilha em Amarelo/Neon
            if (i == selectedIndex) {
                g.setColor(Color.YELLOW);
                g.fillRect(x - 4, gridY - 4, iconSize + 8, iconSize + 8);
            }

            // Fundo do quadradinho
            g.setColor(new Color(45, 45, 85));
            g.fillRect(x, gridY, iconSize, iconSize);
            g.setColor(Color.WHITE);
            g.drawRect(x, gridY, iconSize, iconSize);

            // Carrega o ícone do rosto (profile/icon.png)
            String iconPath = "/sprites/" + f.getSpriteFolder() + "/profile/icon.png";
            Image iconImg = ImageLoader.loadImage(iconPath);
            
            // Fallback: se não tiver foto de perfil ainda, usa o soco/idle 01 reduzido
            if (iconImg == null) {
                iconPath = "/sprites/" + f.getSpriteFolder() + "/idle/01.png";
                iconImg = ImageLoader.loadImage(iconPath);
            }

            if (iconImg != null) {
                g.drawImage(iconImg, x + 5, gridY + 5, iconSize - 10, iconSize - 10, null);
            } else {
                // Caso não ache nada, bota as iniciais
                g.setColor(Color.LIGHT_GRAY);
                g.setFont(g.getFont().deriveFont(Font.BOLD, 14f));
                g.drawString(f.getName().substring(0, Math.min(3, f.getName().length())), x + 20, gridY + 40);
            }
        }


        // ==================== 4. LADO DIREITO: FICHA TÉCNICA DINÂMICA ====================
        int statsX = 330;
        int statsY = 300;

        g.setColor(new Color(25, 25, 45));
        g.fillRect(statsX, statsY, 380, 160);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(statsX, statsY, 380, 160);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 18f));
        g.drawString("FIGHTER STATS:", statsX + 15, statsY + 30);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 16f));
        g.drawString("• MAX HP: " + currentFighter.getMaxHealth(), statsX + 25, statsY + 65);
        g.drawString("• STRENGTH: " + currentFighter.getStrength(), statsX + 25, statsY + 95);
        g.drawString("• SPEED: " + currentFighter.getSpeed(), statsX + 25, statsY + 125);

        // Barra de Defesa Visual
        g.drawString("• DEFENSE: ", statsX + 200, statsY + 65);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(statsX + 200, statsY + 75, 150, 15);
        g.setColor(Color.GREEN);
        int barWidth = (int) (currentFighter.getDefense() * 150);
        g.fillRect(statsX + 200, statsY + 75, barWidth, 15);
        g.setColor(Color.WHITE);
        g.drawString((int)(currentFighter.getDefense() * 100) + "%", statsX + 200, statsY + 115);


        // ==================== 5. RODAPÉ DE COMANDOS ====================
        g.setColor(Color.GRAY);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 16f));
        String footer = "Use ← → to move seletor  |  PRESS ENTER / SPACE TO FIGHT";
        g.drawString(footer, getWidth() / 2 - g.getFontMetrics().stringWidth(footer) / 2, 535);
    }
}