package com.nuutrai.reactor.util;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class FaceToDirection {

    public static Vector get(BlockFace blockFace) {
        return blockFace.getDirection();
    }

}
