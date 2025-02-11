package com.nuutrai.reactor.entity.lang;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.util.MultiTypeMap;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

import static com.nuutrai.reactor.util.ChangeMode.*;

public abstract class CellEntity extends Sellable {

    public CellEntity(String id, Material block) {
        super(id, block);
    }

    @Override
    public void tick() {
        MultiTypeMap map = new MultiTypeMap();

        VecLoc[] neighbours = getPosition().getNeighbours().toArray(new VecLoc[0]);
        ArrayList<Sellable> neighbourEntitiesArray = new ArrayList<>();

        double heatOutput = getHeat();

        if (neighbours.length == 0) {
            DataManager.get(getPlayer()).addHeat(heatOutput);
        }

        for (VecLoc neighbour : neighbours) {
            neighbourEntitiesArray.add(getEntityHandler().get(neighbour));
        }

        health(1, DECREASE);

        Sellable[] neighbourEntities = neighbourEntitiesArray.toArray(new Sellable[0]);

        tick(neighbourEntities, map);
    }
}
