package se.branko.adventure.models;

public abstract class Entity {
    private String role;
    private int health;
    private int damage;

    public Entity(String role, int health, int damage) {
        this.role = role;
        this.health = health;
        this.damage = damage;
    }
    public String getRole() {
        return role;
    }
    public int getHealth() {
        return health;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getDamage() {
        return damage;
    }
    public void attack(Entity target) {
        target.takeDamage(damage);
    }
    public void takeDamage(int damage) {
        health -= damage;
    }
    public boolean isConsious(){
        return health > 0;
    }
}

