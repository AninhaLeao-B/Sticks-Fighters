package com.sticksfighters.fighters;

public class FighterData {

    private final String name;
    private final int maxHealth;
    private final int speed;
    private final int strength;
    private final int range;
    private final double defense;
    private final String spriteFolder;

    public FighterData(String name, int maxHealth, int speed, int strength, int range, double defense, String spriteFolder ) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be positive");
        }
        if (speed < 0 || strength < 0 || range < 0) {
            throw new IllegalArgumentException("Speed, strength and range cannot be negative");
        }
        if (spriteFolder == null || spriteFolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Sprite folder cannot be null or empty");
        }

        this.name = name.trim();
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.strength = strength;
        this.range = range;
        this.defense = defense;
        this.spriteFolder = spriteFolder.trim();
    }

    // Getters
    public String getName() { return name; }
    public int getMaxHealth() { return maxHealth; }
    public int getSpeed() { return speed; }
    public int getStrength() { return strength; }
    public int getRange() { return range; }
    public double getDefense() { return defense; }
    public String getSpriteFolder() { return spriteFolder; }

    @Override
    public String toString() {
        return name + " (HP:" + maxHealth + ", Str:" + strength + ", Spd:" + speed + ")";
    }
}
