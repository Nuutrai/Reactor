package com.nuutrai.reactor.util

import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

object FaceToDirection {
	fun get(blockFace: BlockFace): Vector {
		return blockFace.getDirection()
	}
}
