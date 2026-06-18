package com.sticksfighters.fighters;

public class Fighter {

    private final FighterData data;
    private final String name;
    private final int maxHealth;
    private final int maxSpecialEnergy;

    private int health;
    private int specialEnergy;
    private boolean isAlive;
    private long stunEndTime = 0;
    private boolean isStunned = false;
    private boolean blocking;

    // Construtor
    public Fighter(FighterData data) {
        if (data == null) {
            throw new IllegalArgumentException("FighterData cannot be null");
        }

        this.data = data;
        this.name = data.getName();
        this.maxHealth = data.getMaxHealth();
        this.maxSpecialEnergy = 100;

        this.health = maxHealth;
        this.specialEnergy = 0;
        this.isAlive = true;
    }

    public void receiveDamage(int damage) {
        if (damage <= 0) return;

        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }

        // 🛡️ SÓ ENTRA EM STUN SE NÃO ESTIVER DEFENDENDO!
        if (!this.blocking) {
            this.isStunned = true;
            this.stunEndTime = System.currentTimeMillis() + 750;
        } else {
            System.out.println("🛡️ Bloqueou o impacto! Sem atordoamento.");
        }
    }
    
    public boolean isStunned() {
        if (isStunned && System.currentTimeMillis() > stunEndTime) {
            isStunned = false;
        }
        return isStunned;
    }
    
    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public void heal(int amount) {
        if (amount <= 0) return;

        this.health += amount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public void gainSpecialEnergy(int amount) {
        if (amount <= 0) return;

        this.specialEnergy += amount;
        if (this.specialEnergy > maxSpecialEnergy) {
            this.specialEnergy = maxSpecialEnergy;
        }
    }

    public boolean useSpecialEnergy(int amount) {
        if (amount <= 0) return false;
        if (specialEnergy >= amount) {
            specialEnergy -= amount;
            return true;
        }
        return false;
    }

    // === Getters ===
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isAlive() { return isAlive; }
    public double getHealthPercentage() {
        return maxHealth > 0 ? (double) health / maxHealth * 100 : 0;
    }
    public double getDefense() { 
        return data.getDefense(); 
    }

    public int getSpecialEnergy() { return specialEnergy; }
    public int getMaxSpecialEnergy() { return maxSpecialEnergy; }
    public FighterData getData() { return data; }

    // Delegação para FighterData
    public int getStrength() { return data.getStrength(); }
    public int getSpeed() { return data.getSpeed(); }
    public int getRange() { return data.getRange(); }
    public String getSpriteFolder() { return data.getSpriteFolder(); }

    
    // Útil para testes e resetar entre rounds
        public void reset() {
        this.health = maxHealth;
        this.specialEnergy = 0;
        this.isAlive = true;
        this.isStunned = false;
        this.blocking = false;
    }

    @Override
    public String toString() {
        return name + " [HP: " + health + "/" + maxHealth + " | Energy: " + specialEnergy + "]";
    }
}