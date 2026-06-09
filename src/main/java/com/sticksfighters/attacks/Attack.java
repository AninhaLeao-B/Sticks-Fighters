package com.sticksfighters.attacks;

public class Attack {

    private final String name;
    private final int damage;
    private final int energyCost;
    private final boolean isSpecial;
    private final String description;

    public Attack(String name, int damage, int energyCost, boolean isSpecial, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Attack name cannot be null or empty");
        }
        if (description == null) {
            description = ""; // permite descrição vazia
        }

        this.name = name.trim();
        this.damage = Math.max(0, damage);
        this.energyCost = Math.max(0, energyCost);
        this.isSpecial = isSpecial;
        this.description = description.trim();
    }

    // Getters
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getEnergyCost() { return energyCost; }
    public boolean isSpecial() { return isSpecial; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        String type = isSpecial ? "🔥 Especial" : "Normal";
        return name + " (" + type + ") - Dano: " + damage + " | Energia: " + energyCost;
    }

    // Útil para testes e comparação
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Attack other)) return false;
        return damage == other.damage &&
               energyCost == other.energyCost &&
               isSpecial == other.isSpecial &&
               name.equals(other.name);
    }
}