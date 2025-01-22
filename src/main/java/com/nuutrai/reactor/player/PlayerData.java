package com.nuutrai.reactor.player;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class PlayerData implements Serializable {

    private int balance = 0;
    private Claim claim = null;
    private UUID player;
    private int heat = 0;

    public PlayerData(Player player) {
        this.player = player.getUniqueId();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player.getUniqueId();
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        setHeat(Integer.parseInt(heat));
    }
    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public String toString() {
        return this.player + ".pd";
    }
}
