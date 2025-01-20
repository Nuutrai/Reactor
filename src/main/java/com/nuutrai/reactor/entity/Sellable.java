package com.nuutrai.reactor.entity;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.Serializable;

public abstract class Sellable implements Serializable {

    private String id;
    private Player player;
    private double sellAmount;
    private double heat;
    private Block block;
    private Claim claim;

    public Sellable(String id, Player player, double sellAmount, double heat, Block block, Claim claim) {
        this.id = id;
        this.player = player;
        this.sellAmount = sellAmount;
        this.heat = heat;
        this.block = block;
        this.claim = claim;
    }

    public Sellable(String id, Player player, double sellAmount, double heat, Block block) {
        this.id = id;
        this.player = player;
        this.sellAmount = sellAmount;
        this.heat = heat;
        this.block = block;
        this.claim = DataManager.get(player).getClaim();
    }

    public abstract void tick();

    public abstract void sell();

    public void explode() {
        delete(false);
    }

    public void delete(boolean isDepleted) {
        claim.entityHandler.remove(this);
        if (isDepleted) {

        }
    }

    public boolean equals(Sellable s) {
        return this.id.equals(s.id);
    }

}
