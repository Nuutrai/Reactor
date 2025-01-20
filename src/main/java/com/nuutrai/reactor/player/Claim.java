package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.EntityHandler;
import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class Claim implements Serializable {

    public EntityHandler entityHandler = new EntityHandler();
    private UUID player;
    private Bound bound;

    public Claim(Player player, Bound bound) {
        this.player = player.getUniqueId();
        this.bound = bound;
    }

    public UUID getPlayer() {
        return player;
    }

    public void newEntity(Sellable sellable) {
        entityHandler.add(sellable);
    }
}
