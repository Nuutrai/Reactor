package com.nuutrai.reactor.entity;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.Claim;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CellEntity extends Sellable {

    public CellEntity(String id, Player player, double sellAmount, double heat, Block block, Claim claim) {
        super(id, player, sellAmount, 0, block, claim);
    }



    @Override
    public void tick() {

    }

    @Override
    public void sell() {

    }

}
