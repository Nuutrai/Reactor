package com.nuutrai.reactor.entity

import com.nuutrai.reactor.util.Settable
import net.kyori.adventure.util.TriState
import org.jetbrains.annotations.ApiStatus

/**
 * This is going to be a feature we'll implement after we figure out how to reliably replicate how reactor incremental does theirs
 */
@ApiStatus.Experimental
interface Pairable {
	fun setPair(): Pairable? {
		return if (!Companion.pair.isNull) Companion.pair.get() else null
	}

	var pair: Pairable?
		get() = Companion.pair.get()
		set(pair) {
			Companion.pair.set(pair)
		}

	@ApiStatus.Experimental
	fun setPair(neighbours: Array<Sellable?>): TriState {
		for (neighbour in neighbours) {
			val state = setPair(neighbour)
			if (state == TriState.TRUE) {
				return TriState.TRUE
			} else if (state == TriState.NOT_SET) {
				return TriState.NOT_SET
			}
		}

		return TriState.NOT_SET
	}

	@ApiStatus.Experimental
	fun setPair(potentialPair: Sellable?): TriState {
		if (potentialPair !is Pairable) return TriState.FALSE
		if (potentialPair.pair != null) return TriState.NOT_SET
		potentialPair.pair = this
		this.pair = potentialPair
		return TriState.TRUE
	}

	companion object {
		val pair: Settable<Pairable?> = Settable<Pairable?>()
	}
}
