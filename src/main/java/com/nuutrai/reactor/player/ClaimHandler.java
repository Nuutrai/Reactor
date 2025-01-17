package com.nuutrai.reactor.player;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class ClaimHandler {

    private static ArrayList<Claim> claims = new ArrayList<>();

    public static void add(Claim claim) {
        claims.add(claim);
    }

    public static void remove(Claim claim) {
        claims.remove(claim);
    }

    public static ArrayList<Claim> getClaims() {
        return claims;
    }

    public static ArrayList<Claim> getClaimsOfPlayer(Player player) {
        ArrayList<Claim> claimsOfPlayer = new ArrayList<>();
        for (Claim claim: getClaims()) {
            if (player.equals(claim.getPlayer()))
                claimsOfPlayer.add(claim);
        }
        return claimsOfPlayer;
    }

}
