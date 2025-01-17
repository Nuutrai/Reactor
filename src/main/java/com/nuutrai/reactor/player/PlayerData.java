package com.nuutrai.reactor.player;

import org.bukkit.entity.Player;

import java.io.Serializable;

public class PlayerData implements Serializable {

    private int balance;
    private Claim claim;
    private Player player;
    private int heat;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

}
