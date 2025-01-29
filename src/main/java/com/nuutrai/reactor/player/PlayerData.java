package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.EntityHandler;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.UUID;

public class PlayerData implements Serializable {

    private int balance = 0;
    private EntityHandler entities = new EntityHandler();
    private int heat = 0;
    private int power = 0;
    public  ItemStack selection = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE);
    private boolean isPaused = false;
    private Player player;

    public PlayerData() {
    }

    public PlayerData(PlayerDataWrapper playerDataWrapper) {
        this.balance = playerDataWrapper.getBalance();
        this.heat = playerDataWrapper.getHeat();
        this.power = playerDataWrapper.getPower();
        this.isPaused = playerDataWrapper.isPaused();
        for (VecLoc loc: playerDataWrapper.getLocations()) {
            Sellable s = playerDataWrapper.getEntities().get(loc);
            this.entities.add(s, loc);
        }
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addEntity(Sellable s, VecLoc location) {
        entities.add(s, location);
    }

    public void removeEntity(VecLoc location) {
        entities.remove(location);
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

    public EntityHandler getEntities() {
        return entities;
    }

    public void setPaused(boolean pause) {
        isPaused = pause;
    }

    public void tick() {
        entities.tick();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
