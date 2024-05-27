package com.example.mymod;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerStats {
    private int level;
    private int experience;
    private int strength;
    private int agility;
    private int intelligence;
    private int points;

    public PlayerStats() {
        this.level = 1;
        this.experience = 0;
        this.strength = 0;
        this.agility = 0;
        this.intelligence = 0;
        this.points = 10;
    }

    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getStrength() { return strength; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getPoints() { return points; }

    public void addExperience(int exp) {
        this.experience += exp;
        while (this.experience >= getExperienceForNextLevel()) {
            this.experience -= getExperienceForNextLevel();
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;
        this.points += 5;
    }

    public int getExperienceForNextLevel() {
        return level * 100;
    }

    public void increaseStrength() {
        if (points > 0) {
            this.strength++;
            this.points--;
        }
    }

    public void increaseAgility() {
        if (points > 0) {
            this.agility++;
            this.points--;
        }
    }

    public void increaseIntelligence() {
        if (points > 0) {
            this.intelligence++;
            this.points--;
        }
    }

    public void applyEffects(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(1.0 + strength * 0.2);
        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 + agility * 0.02);
        player.getAttributeInstance(EntityAttributes.GENERIC_LUCK).setBaseValue(0.0 + intelligence * 0.1);
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0 + strength * 2.0);
        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED).setBaseValue(4.0 + agility * 0.1);
    }

    public void fromNbt(NbtCompound nbt) {
        this.level = nbt.getInt("level");
        this.experience = nbt.getInt("experience");
        this.strength = nbt.getInt("strength");
        this.agility = nbt.getInt("agility");
        this.intelligence = nbt.getInt("intelligence");
        this.points = nbt.getInt("points");
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("level", level);
        nbt.putInt("experience", experience);
        nbt.putInt("strength", strength);
        nbt.putInt("agility", agility);
        nbt.putInt("intelligence", intelligence);
        nbt.putInt("points", points);
        return nbt;
    }
}
