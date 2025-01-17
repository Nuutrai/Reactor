package com.nuutrai.reactor.player;

import com.nuutrai.reactor.Reactor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class Tick {

    public static void beginTick() {
        tick();
    }

    private static @NotNull Runnable tick() {

        if (Reactor.HALTTICK) {
            return null;
        }

        for (Claim claim: ClaimHandler.getClaims()) {
            claim.entityHandler.tick();
        }
        try {
            Bukkit.getScheduler().runTaskLater(Reactor.instance, tick(), 1000);
        } catch (IllegalArgumentException e) {
            Reactor.logger.info("Tick has been halted");
        }

        // This might work?
        // I just don't want a bunch of the above errors sending

        return () -> {};
    }

}
