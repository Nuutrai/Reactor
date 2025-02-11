package com.nuutrai.reactor.entity.impl.cell;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Pairable;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.lang.CellEntity;
import com.nuutrai.reactor.entity.lang.VentEntity;
import com.nuutrai.reactor.util.MultiTypeMap;
import org.bukkit.Material;

import static com.nuutrai.reactor.util.ChangeMode.ADD;

public class UraniumCellEntity extends CellEntity implements Pairable {

    public UraniumCellEntity() {
        super("uranium_single", Material.EMERALD_BLOCK);
    }

    @Override
    public void tick(Sellable[] neighbours, MultiTypeMap params) {
        double heatPerNeighbour = params.get("heatPer", double.class);

        for (Sellable neighbour: neighbours) {
            if (neighbour instanceof VentEntity vent) {
                vent.health(heatPerNeighbour, ADD);
            }
        }
        System.out.printf("Ticked Cell %s, increased power by %s%n", getPosition().toString(), getPower());
        DataManager.get(getPlayer()).addPower(getPower());

    }

    @Override
    public double getSellAmount() {
        return 0;
    }

    @Override
    public void sell() {

    }

    @Override
    public Sellable clone() {
        return new UraniumCellEntity();
    }


}
