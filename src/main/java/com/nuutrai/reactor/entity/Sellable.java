package com.nuutrai.reactor.entity;

import com.nuutrai.reactor.item.Buyable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class Sellable implements Serializable {

    private final Buyable type;
    private final Material block;
    private final double maxHealth;
    private Player player = null;
    private Location position = null;
    private double currentHealth;

    public Sellable(Buyable type, Player player, Location position, Material block) {
        this.type = type;
        this.player = player;
        this.position = position;
        this.block = block;
        this.maxHealth = type.getHealth();
    }

    public Sellable(Buyable type, Material block) {
        this.type = type;
        this.block = block;
        this.maxHealth = type.getHealth();
    }

    public static void create(Sellable s) {
        // No clue at the moment, just here for now
    }

    public static Sellable create(Sellable sellable, Player player, Location position) {
        sellable.player = player;
        sellable.position = position;

        // Make block at position

        return sellable;
    }

    public Buyable getType() {
        return type;
    }

    public Material getBlock() {
        return block;
    }

    public Location getPosition() {
        return this.position;
    }

    private double getHeat() {
        return type.getHeat();
    }

    private double getHealth() {
        return type.getHealth();
    }

    protected float getHeatToCostRatio() {
        return (float) (getHeat() / type.getCost());
    }

    public abstract void tick();

    public abstract double getSellAmount();

    public abstract void sell();

    public void explode() {
        delete(false);
    }

    public void delete(boolean isDepleted) {
        if (isDepleted) {
            // Do stuff for turning into *nothing*
        }
    }

    public abstract Sellable clone();

    public boolean equals(Sellable s) {
        return this.type.getId().equals(s.getType().getId());
    }

}
