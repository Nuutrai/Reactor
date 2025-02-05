package com.nuutrai.reactor.entity.impl.cell;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.impl.vent.VentEntity;
import com.nuutrai.reactor.util.ChangeMode;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Material;

import java.util.ArrayList;

import static com.nuutrai.reactor.util.ChangeMode.*;

public abstract class CellEntity extends Sellable {
    public CellEntity(String id, Material block) {
        super(id, block);
    }

    @Override
    public void tick() {
        VecLoc[] neighbours = getPosition().getNeighbours().toArray(new VecLoc[0]);
        ArrayList<Sellable> neighbourEntitiesArray = new ArrayList<>();
        double heatOutput = getHeat();
        double heatPerNeighbour = heatOutput / neighbours.length;

        for (VecLoc neighbour : neighbours) {
            neighbourEntitiesArray.add(getEntityHandler().get(neighbour));
        }

        health(1, DECREASE);

        Sellable[] neighbourEntities = (Sellable[]) neighbourEntitiesArray.toArray();
        tick(neighbourEntities, heatPerNeighbour);
    }
}
