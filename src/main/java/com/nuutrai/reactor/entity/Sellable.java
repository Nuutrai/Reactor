package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.item.Buyable;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public abstract class Sellable implements Serializable {

    private final Buyable type;
    private final Material block;
    private final double maxHealth;
    private UUID player = null;
    private VecLoc position = null;
    private double currentHealth;
    public static final Map<String, Sellable> SELLABLES = Maps.newHashMap();

    public Sellable(Buyable type, Player player, Location position, Material block) {
        this.type = type;
        this.player = player.getUniqueId();
        this.position = new VecLoc(position, player.getUniqueId());
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
        sellable.player = player.getUniqueId();
        sellable.position = new VecLoc(position, player.getUniqueId());

        // Make block at position

        return sellable;
    }

    public static Sellable get(String id) {
        return SELLABLES.get(id);
    }

    public Buyable getType() {
        return type;
    }

    public Material getBlock() {
        return block;
    }

    public VecLoc getPosition() {
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

    public double getMaxHealth() {
        return maxHealth;
    }

    public UUID getPlayer() {
        return player;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }
}
