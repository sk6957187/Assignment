package com.game.project.GameV1.model;

public class Car {
    private String name;
    private int health;
    private int attackPower;
    private int defenseStrength;

    public Car(String name, int health, int attackPower, int defenseStrength) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defenseStrength = defenseStrength;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefenseStrength() {
        return defenseStrength;
    }

    public void setDefenseStrength(int defenseStrength) {
        this.defenseStrength = defenseStrength;
    }
}
