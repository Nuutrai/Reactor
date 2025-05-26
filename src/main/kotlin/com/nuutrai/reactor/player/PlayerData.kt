package com.nuutrai.reactor.player;

import com.nuutrai.reactor.entity.EntityHandler;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.util.VecLoc;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;

import static com.nuutrai.reactor.Reactor.instance;

public class PlayerData implements Serializable {

    private int balance = 0;
    private EntityHandler entities = new EntityHandler();
    private double heat = 0;
    private int power = 0;
    public  ItemStack selection = ItemStack.of(Material.AIR);
    private boolean isPaused = true;
    private Player player;

    public PlayerData() {
    }

    public PlayerData(PlayerDataWrapper playerDataWrapper) {
        this.balance = playerDataWrapper.getBalance();
        this.heat = playerDataWrapper.getHeat();
        this.power = playerDataWrapper.getPower();
        for (VecLoc loc: playerDataWrapper.getLocations()) {
            Sellable s = playerDataWrapper.getEntities().get(loc);
            this.entities.add(s, loc);
        }
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int by) {
        this.balance += by;
    }

    public void removeBalance(int by) {
        this.balance += by;
    }

    public void addEntity(Sellable s, VecLoc location) {
        entities.add(s, location);
    }

    public void removeEntity(VecLoc location) {
        entities.remove(location);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void addPower(int power) {
        this.power += power;
    }

    public double getHeat() {
        return heat;
    }

    public void addHeat(double heat) {
        this.heat += heat;
    }
    
    public void setHeat(double heat) {
        this.heat = heat;
    }

    public EntityHandler getEntities() {
        return entities;
    }

    public void setPaused(boolean pause) {
        isPaused = pause;
    }

    public void loadAllEntities() {
        entities.place();
    }

    public void loadEntity(VecLoc loc) {
        entities.place(loc);
    }

    public void loadEntity(Location loc) {
        loadEntity(new VecLoc(loc, player.getUniqueId()));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void tick() {
        if (!isPaused()) {
            entities.tick();
        }

        Bukkit.getScheduler().runTask(instance, () -> {
            player.getInventory().setItem(40, determinePauseItem());
        });

        update();
        
    }

    private void update() {

    }

    public ItemStack determinePauseItem() {
        ItemStack pause;
        NamedTextColor pauseColour = NamedTextColor.RED;
        if (isPaused()) {
            pause = ItemStack.of(Material.FIREWORK_STAR);
            pauseColour = NamedTextColor.GRAY;
        } else {
            pause = ItemStack.of(Material.FIRE_CHARGE);
        }

        ItemMeta pauseMeta = pause.getItemMeta();
        pauseMeta.displayName(Component.text("Pause", pauseColour).decoration(TextDecoration.ITALIC, false));
        pause.setItemMeta(pauseMeta);
        return pause;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
