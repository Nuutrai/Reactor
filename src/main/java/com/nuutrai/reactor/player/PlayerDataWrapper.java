package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.impl.cell.UraniumCellEntity;
import com.nuutrai.reactor.util.VecLoc;
import io.papermc.paper.entity.TeleportFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataWrapper implements Serializable {

    private int balance = 0;
    private HashMap<VecLoc, Sellable> entities;
    private ArrayList<VecLoc> locations;
    private int heat = 0;
    private int power = 0;

    public PlayerDataWrapper(PlayerData pd) {
        this.balance = pd.getBalance();
        this.heat = pd.getHeat();
        this.power = pd.getPower();
        this.entities = pd.getEntities().getEntityMap();
        this.locations = pd.getEntities().getLocations();
    }

    public PlayerDataWrapper(int balance, HashMap<VecLoc, Sellable> entities, ArrayList<VecLoc> locations, int heat, int power) {
        this.balance = balance;
        this.entities = entities;
        this.locations = locations;
        this.heat = heat;
        this.power = power;
    }

    public int getBalance() {
        return balance;
    }

    public int getHeat() {
        return heat;
    }

    public HashMap<VecLoc, Sellable> getEntities() {
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
