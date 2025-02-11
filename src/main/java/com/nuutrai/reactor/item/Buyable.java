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

/**
 *
 * @id The type or identifier of any reactor
 * @cost The cost of the item
 * @name The name shown in the store
 * @description The description shown in the store
 * @heat The amount of heat produced in a tick
 * @health The max amount of heat for an item
 * @item The item displayed in the store
 *
 */
public abstract class Buyable {

    private final String id;
    private final int cost;
    private final String name;
    private final String description;
    private double heat;
    private int power;
    private final ItemStack item;
    /**
     *  Health represents both durability & heat content.
     */
    private double health;

    private static final Map<String, Buyable> BUYABLES = Maps.newHashMap();

    public Buyable(String id, int cost, String name, String description, double heat, int power, double health, ItemStack item) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.heat = heat;
        this.power = power;
        this.item = item;
        this.health = health;
    }

    public Buyable(String id, int cost, String name, String description, double heat, int power, double health, NamedTextColor colour, Material item) {
        this.heat = heat;
        this.health = health;
        this.power = power;
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

    public int getPower() {
        return power;
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
