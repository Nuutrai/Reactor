package com.nuutrai.reactor.world;

import com.nuutrai.reactor.Reactor;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    private Reactor plugin;
    private final int yValue = 120;

    public VoidGenerator(Reactor plugin) {
        this.plugin = plugin;
    }

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {

        if ((chunkX == 0 || chunkX == -1) && (chunkZ == 0 || chunkZ == -1)) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Material material = Material.SMOOTH_STONE;
                    if (((x + chunkX * 16) == -16 || (x + chunkX * 16) == 15) || ((z + chunkZ * 16) == -16 || (z + chunkZ * 16) == 15)) {
                        chunkData.setBlock(x, yValue + 1, z, Material.IRON_BLOCK);
                        material = Material.BEDROCK;
                    }
                    chunkData.setBlock(x, yValue, z, material);
                }
            }
        }
    }

}
