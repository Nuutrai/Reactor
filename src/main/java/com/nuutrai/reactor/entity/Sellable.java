package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.google.gson.*;
import com.nuutrai.reactor.item.Buyable;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

/**
 * TODO:
 * <p>
 * Setup ids when possible
 */

public abstract class Sellable implements JsonSerializer<Sellable>, JsonDeserializer<Sellable> {

    private final String id;
    private final Material block;
    private final double maxHealth;
    private UUID player = null;
    private VecLoc position = null;
    private double currentHealth;
    private static final Map<String, Sellable> SELLABLES = Maps.newHashMap();

    public Sellable(String id, Material block, Player player, Location position) {
        this.id = id;
        this.player = player.getUniqueId();
        this.position = new VecLoc(position, player.getUniqueId());
        this.block = block;
        this.maxHealth = getType().getHealth();
    }

    public Sellable(String id, Material block) {
        this.id = id;
        this.block = block;
        this.maxHealth = getType().getHealth();
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

    public static void add(Sellable s) {
        SELLABLES.put(s.getType().getId(), s);
    }

    public String getId() {
        return id;
    }

    public Buyable getType() {
        return Buyable.get(id);
    }

    public Material getBlock() {
        return block;
    }

    public VecLoc getPosition() {
        return this.position;
    }

    private double getHeat() {
        return getType().getHeat();
    }

    private double getHealth() {
        return getType().getHealth();
    }

    protected float getHeatToCostRatio() {
        return (float) (getHeat() / getType().getCost());
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
        return id.equals(s.getId());
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

    @Override
    public Sellable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        json.

        return null;
    }

}
