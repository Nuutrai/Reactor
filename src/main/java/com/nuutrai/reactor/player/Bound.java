package com.nuutrai.reactor.player;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Bound {

    private UUID owner;
    private Vector min;
    private Vector max;

    public Bound(Player owner, Vector min, Vector max) {
        this.owner = owner.getUniqueId();
        this.min = min;
        this.max = max;
    }

    public UUID getOwner() {
        return owner;
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

}
