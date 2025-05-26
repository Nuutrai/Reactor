package com.nuutrai.reactor.world

import com.nuutrai.reactor.Reactor
import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*

class VoidGenerator(private val plugin: Reactor?) : ChunkGenerator() {
	private val yValue = 120

	override fun generateBedrock(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
		if ((chunkX == 0 || chunkX == -1) && (chunkZ == 0 || chunkZ == -1)) {
			for (x in 0..15) {
				for (z in 0..15) {
					var material = Material.SMOOTH_STONE
					if (((x + chunkX * 16) == -16 || (x + chunkX * 16) == 15) || ((z + chunkZ * 16) == -16 || (z + chunkZ * 16) == 15)) {
						chunkData.setBlock(x, yValue + 1, z, Material.IRON_BLOCK)
						material = Material.BEDROCK
					}
					chunkData.setBlock(x, yValue, z, material)
				}
			}
		}
	}
}
