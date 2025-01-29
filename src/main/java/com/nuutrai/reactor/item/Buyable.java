package com.nuutrai.reactor.item;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Sellable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Map;

public abstract class Buyable {

    private final String id;
    private final int cost;
    private final String name;
    private final String description;
    private double heat;
    private final ItemStack item;

    /**
     *  Health represents both durability & heat content.
     */
    private double health;
    // Needed?
    //    private boolean isDurability;

    private static final Map<String, Buyable> BUYABLES = Maps.newHashMap();

    public Buyable(String id, int cost, String name, String description, ItemStack item) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.item = item;
    }

    public Buyable(String id, int cost, String name, String description, NamedTextColor colour, Material item) {
        ItemStack itemWithMeta = ItemStack.of(item);
        ItemMeta meta = itemWithMeta.getItemMeta();
        NamespacedKey key = new NamespacedKey(Reactor.instance, "reactor-id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);

        meta.displayName(Component.text(name, colour).decoration(TextDecoration.ITALIC, false));

        itemWithMeta.setItemMeta(meta);

        this.id = id;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.item = itemWithMeta;

    }

    public static Buyable get(String id) {
        return BUYABLES.get(id);
    }

    public static void add(Buyable b) {
        BUYABLES.put(b.getId(), b);
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public double getHealth() {
        return health;
    }

    public double getHeat() {
        return heat;
    }

    public ItemStack getItem() {
        return item;
    }

    // Needed here? Probably put in store stuff
    public void buy(Player player, Buyable item) {
        int balance = DataManager.get(player).getBalance();
        DataManager.get(player).setBalance(balance-item.cost);
    }


    public boolean equals(Buyable b) {
        return this.id.equals(b.id);
    }

}
