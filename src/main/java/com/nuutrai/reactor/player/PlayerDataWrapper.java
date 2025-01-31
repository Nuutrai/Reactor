package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.impl.cell.UraniumCellEntity;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Location;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataWrapper implements Serializable {

    private int balance = 0;
    private HashMap<Integer, Sellable> entities;
    private ArrayList<VecLoc> locations;
    private int heat = 0;
    private int power = 0;
    private boolean isPaused = false;

    public PlayerDataWrapper(PlayerData pd) {
        this.balance = pd.getBalance();
        this.heat = pd.getHeat();
        this.power = pd.getPower();
        this.isPaused = pd.isPaused();
        this.entities = pd.getEntities().getEntityMap();
        this.locations = pd.getEntities().getLocations();
    }

    public int getBalance() {
        return balance;
    }

    public int getHeat() {
        return heat;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public HashMap<Integer, Sellable> getEntities() {
        return entities;
    }

    public ArrayList<VecLoc> getLocations() {
        return locations;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
