package com.nuutrai.reactor.entity;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.player.ClaimHandler;
import com.nuutrai.reactor.item.Buyable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class Sellable implements Serializable {

    private final Buyable type;
    private Player player = null;
    private Location position = null;
//    private Claim claim = null;
    private final Material block;
    private double currentHealth;
    private final double maxHealth;

    public Sellable(Buyable type, Player player, Location position, Claim claim, Material block) {
        this.type = type;
        this.player = player;
        this.position = position;
//        this.claim = claim;
        this.block = block;
        this.maxHealth = type.getHealth();
    }

    public Sellable(Buyable type, Player player, Location position, Material block) {
        this.type = type;
        this.player = player;
        this.position = position;
//        this.claim = DataManager.get(player).getClaim();
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

    public Buyable getType() {
        return type;
    }

    public Material getBlock() {
        return block;
    }

    private double getHeat() {
        return type.getHeat();
    }

    private double getHealth() {
        return type.getHealth();
    }

    protected float getHeatToCostRatio() {
        return (float) (getHeat()/type.getCost());
    }

    public abstract void tick();

    public abstract double getSellAmount();

    public abstract void sell();

    public static Sellable create(Sellable sellable, Player player, Location position) {
//        sellable.claim = ClaimHandler.getClaimOfPlayer(player);
        sellable.player = player;
        sellable.position = position;

        // Make block at position

        return sellable;
    }


    public void explode() {
        delete(false);
    }

    public void delete(boolean isDepleted) {
//        claim.entityHandler.remove(this);
        if (isDepleted) {
            // Do stuff for turning into *nothing*
        }
    }

    public abstract Sellable clone();

    public boolean equals(Sellable s) {
        return this.type.getId().equals(s.getType().getId());
    }

}
