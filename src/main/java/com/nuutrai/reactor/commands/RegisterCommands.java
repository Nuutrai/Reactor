package com.nuutrai.reactor.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.store.Store;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Warning;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static com.nuutrai.reactor.Reactor.logger;
import static io.papermc.paper.command.brigadier.Commands.argument;

@SuppressWarnings("UnstableApiUsage")
public class RegisterCommands {

    public static void loadInventoryTest() {
        Reactor.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("testInv")
                            .executes(ctx -> {
                                if (ctx.getSource().getSender() instanceof Player player) {
                                    Store.setup(player);
                                    return Command.SINGLE_SUCCESS;
                                }
                                return 0;
                            })
                            .build()
            );
        });
    }

    @SuppressWarnings("NullableProblems")
    @Deprecated
        public static void loadDataEditor() {
            LifecycleEventManager<Plugin> manager = Reactor.instance.getLifecycleManager();
            manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
                final Commands commands = event.registrar();
                commands.register(
                        Commands.literal("setData")
                                .then(argument("name", StringArgumentType.word())
                                        .then(argument("value", StringArgumentType.greedyString())
                                            .executes(ctx -> {
                                                CommandSender sender = ctx.getSource().getSender();
                                                if (!(sender instanceof Player player))
                                                    return 0;

                                                PlayerData playerData = DataManager.get(player);
                                                String name = StringArgumentType.getString(ctx, "name");
                                                String value = StringArgumentType.getString(ctx, "value");

                                                logger.info("Hello??");
                                                logger.info(playerData.toString());
                                                logger.info(name);
                                                logger.info(value);

                                                try {
                                                    PlayerData.class.getMethod(name, String.class).invoke(playerData, value);
                                                    return Command.SINGLE_SUCCESS;
                                                } catch (IllegalAccessException | NoSuchMethodException |
                                                         InvocationTargetException e) {
                                                    throw new RuntimeException(e);
                                                }

                                            }))
                                        .executes(ctx -> {
                                            ctx.getSource().getSender().sendMessage("Uh, what was that?");
                                            return 0;
                                        }))
                                .executes(ctx -> {
                                    ctx.getSource().getSender().sendMessage("Uh, what was that?");
                                    return 0;
                                })
                                .build(),
                        "some bukkit help description string",
                        List.of("an-alias")
                );
            });
        }

}
