package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.util.VecLoc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDataWrapper implements Serializable {

    private int balance = 0;
    private HashMap<VecLoc, Sellable> entities;
    private ArrayList<VecLoc> locations;
    private double heat = 0;
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

    public double getHeat() {
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
