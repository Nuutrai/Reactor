//package com.nuutrai.reactor.player;
//
//import com.nuutrai.reactor.util.Compare;
//import org.bukkit.entity.Player;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class ClaimHandler {
//
//    private static final ArrayList<Claim> claims = new ArrayList<>();
//
//    public static void add(Claim claim) {
//        claims.add(claim);
//    }
//
//    public static void remove(Claim claim) {
//        claims.remove(claim);
//    }
//
//    public static ArrayList<Claim> getClaims() {
//        return claims;
//    }
//
//    public static Claim getClaimOfPlayer(Player player) {
//        for (Claim claim: getClaims()) {
//            if (Compare.player(player, claim.getPlayer()))
//                return claim;
//        }
//        return null;
//    }
//
//}
