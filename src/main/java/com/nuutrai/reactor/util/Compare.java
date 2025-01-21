package com.nuutrai.reactor.util;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Compare {

    public static boolean player(Player player1, UUID player2) {
        return player1.getUniqueId().equals(player2);
    }

}
