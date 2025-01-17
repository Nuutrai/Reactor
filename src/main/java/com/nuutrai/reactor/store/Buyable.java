package com.nuutrai.reactor.store;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.xml.crypto.Data;

public abstract class Buyable {

    private String id;
    private int cost;
    private String name;
    private String description;
    private Material item;

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

    public void buy(Player player, Buyable item) {
        int balance = DataManager.get(player).getBalance();
        DataManager.get(player).setBalance(balance-item.cost);
    }


    public boolean equals(Buyable s) {
        return this.id.equals(s.id);
    }

}
