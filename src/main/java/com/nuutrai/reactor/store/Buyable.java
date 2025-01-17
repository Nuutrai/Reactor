package com.nuutrai.reactor.store;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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
        int balance = PlayerData.getBalance(player);
        PlayerData.setBalance(player, balance-item.cost);
    }


    public boolean equals(Buyable s) {
        return this.id.equals(s.id);
    }

}
