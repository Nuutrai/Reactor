package com.nuutrai.reactor.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

import static com.nuutrai.reactor.Reactor.logger;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class RegisterCommands {

        @SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
        public void loadDataEditor() {
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
                                                try {
                                                    String name = StringArgumentType.getString(ctx, "name"));

                                                    PlayerData.class.getField(name).set(playerData, (PlayerData.class.getField(name).getType()) );
                                                } catch (Exception e) {
                                                    logger.severe(Arrays.toString(e.getStackTrace()));
                                                }

                                                return Command.SINGLE_SUCCESS;
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
