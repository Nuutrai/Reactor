package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerData {

    private int balance;


    public Sellable getFromLocation(Vector pos) {
//        for
        return null;
    }

    public Claim getClaim(Player player) {
        return new Claim(player, new Bound(player, new Vector(0,0,0), new Vector(0,0,0)));
    }

    public void loadPlayerData(Player player) {

        // Put handler stuff here

        return;
    }



}
