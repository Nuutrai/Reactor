package com.nuutrai.reactor.entity;

import com.nuutrai.reactor.util.Settable;
import net.kyori.adventure.util.TriState;
import org.jetbrains.annotations.ApiStatus.Experimental;

/**
 * This is going to be a feature we'll implement after we figure out how to reliably replicate how reactor incremental does theirs
 */

@Experimental
public interface Pairable {

    Settable<Pairable> pair = new Settable<>();

    default Pairable setPair() {
        return !pair.isNull() ? pair.get() : null;
    }

    default Pairable getPair() {
        return pair.get();
    }

    default void setPair(Pairable pair) {
        this.pair.set(pair);
    }

    @Experimental
    default TriState setPair(Sellable[] neighbours) {
        for (Sellable neighbour: neighbours) {
            TriState state = setPair(neighbour);
            if (state.equals(TriState.TRUE)) {
                return TriState.TRUE;
            } else if (state.equals(TriState.NOT_SET)) {
                return TriState.NOT_SET;
            }
        }

        return TriState.NOT_SET;
    }

    @Experimental
    default TriState setPair(Sellable potentialPair) {
        if (!(potentialPair instanceof Pairable pair))
            return TriState.FALSE;
        if (pair.getPair() != null)
            return TriState.NOT_SET;
        pair.setPair(this);
        this.setPair(pair);
        return TriState.TRUE;
    }
}
