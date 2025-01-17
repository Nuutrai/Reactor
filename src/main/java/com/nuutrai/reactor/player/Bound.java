package com.nuutrai.reactor.player;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Bound {

    private Player owner;
    private Vector min;
    private Vector max;

    public Bound(Player owner, Vector min, Vector max) {
        this.owner = owner;
        this.min = min;
        this.max = max;
    }

    public Player getOwner() {
        return owner;
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

}
