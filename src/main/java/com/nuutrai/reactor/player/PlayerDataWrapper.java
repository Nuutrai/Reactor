package com.nuutrai.reactor.player;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerDataWrapper {

    private int balance = 0;
    private Claim claim = null;
    private final UUID player;
    private int heat = 0;
    private int power = 0;
    private boolean isPaused = false;

    public PlayerDataWrapper(PlayerData pd) {
        this.balance = pd.getBalance();
        this.claim = pd.getClaim();
        this.player = pd.getPlayer();
        this.heat = pd.getHeat();
        this.power = pd.getPower();
        this.isPaused = pd.isPaused();
    }

    public int getBalance() {
        return balance;
    }

    public Claim getClaim() {
        return claim;
    }

    public UUID getPlayer() {
        return player;
    }

    public int getHeat() {
        return heat;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
