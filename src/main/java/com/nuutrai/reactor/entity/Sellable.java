package com.nuutrai.reactor.entity;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.player.ClaimHandler;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.store.Buyable;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.Serializable;

public abstract class Sellable implements Serializable {

    private Buyable type;
    private Player player = null;
    private Block position = null;
    private Claim claim = null;
    private Material block;
    private double currentHealth;
    private double maxHealth;

    public Sellable(Buyable type, Player player, Block position, Claim claim, Material block) {
        this.type = type;
        this.player = player;
        this.position = position;
        this.claim = claim;
        this.block = block;
        this.maxHealth = type.getHealth();
    }

    public Sellable(Buyable type, Player player, Block position, Material block) {
        this.type = type;
        this.player = player;
        this.position = position;
        this.claim = DataManager.get(player).getClaim();
        this.block = block;
        this.maxHealth = type.getHealth();
    }

    public Sellable(Buyable type, Material block) {
        this.type = type;
        this.block = block;
        this.maxHealth = type.getHealth();
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

    public static Sellable create(Sellable sellable, Player player, Block position) {
        sellable.claim = ClaimHandler.getClaimOfPlayer(player);
        sellable.player = player;
        sellable.position = position;

        // Make block at position

        return sellable;
    }


    public void explode() {
        delete(false);
    }

    public void delete(boolean isDepleted) {
        claim.entityHandler.remove(this);
        if (isDepleted) {
            // Do stuff for turning into *nothing*
        }
    }

    public boolean equals(Sellable s) {
        return this.type.getId().equals(s.getType().getId());
    }

}
