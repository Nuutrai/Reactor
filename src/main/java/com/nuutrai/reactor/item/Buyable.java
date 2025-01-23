package com.nuutrai.reactor.item;

import com.nuutrai.reactor.data.DataManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Buyable {

    private String id;
    private int cost;
    private String name;
    private String description;
    private double heat;
    private Material item;

    /**
     *  Health represents both durability & heat content.
     */
    private double health;
    // Needed?
    //    private boolean isDurability;

    public Buyable(String id, int cost, String name, String description, Material item) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public double getHealth() {
        return health;
    }

    public double getHeat() {
        return heat;
    }

    public Material getItem() {
        return item;
    }

    // Needed here? Probably put in store stuff
    public void buy(Player player, Buyable item) {
        int balance = DataManager.get(player).getBalance();
        DataManager.get(player).setBalance(balance-item.cost);
    }


    public boolean equals(Buyable s) {
        return this.id.equals(s.id);
    }

}
