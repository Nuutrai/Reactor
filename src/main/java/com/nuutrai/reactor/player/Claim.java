package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.EntityHandler;
import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.entity.Player;

public class Claim {

    public EntityHandler entityHandler = new EntityHandler();
    private Player player;
    private Bound bound;

    public Claim(Player player, Bound bound) {
        this.player = player;
        this.bound = bound;
    }

    public Player getPlayer() {
        return player;
    }

    public void newEntity(Sellable sellable) {
        entityHandler.add(sellable);
    }
}
