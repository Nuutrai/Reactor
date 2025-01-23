package com.nuutrai.reactor.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.UUID;

public class PlayerData implements Serializable {

    private int balance = 0;

    // May change to a world instead, making this pretty much useless
    private Claim claim = null;
    private UUID player;
    private int heat = 0;
    private int power = 0;
    public ItemStack selection = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE);
    private boolean isPaused = false;

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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void addPower(int power) {
        this.power += power;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public String toString() {
        return this.player + ".json";
    }

    public boolean isPaused() {
        return isPaused;
    }
}
