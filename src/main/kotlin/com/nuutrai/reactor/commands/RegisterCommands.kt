@file:Suppress("UnstableApiUsage")

package com.nuutrai.reactor.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager.get
import com.nuutrai.reactor.player.PlayerData
import com.nuutrai.reactor.store.Store.setup
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.handler.LifecycleEventHandler
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.entity.Player
import java.lang.Deprecated
import java.lang.reflect.InvocationTargetException
import kotlin.RuntimeException
import kotlin.String
import kotlin.Suppress
import kotlin.Throws
import kotlin.toString

object RegisterCommands {
	@Throws(InvocationTargetException::class, IllegalAccessException::class)
	fun load() {
		val methods = RegisterCommands::class.java.getDeclaredMethods()
		for (m in methods) {
			if (!m.isAnnotationPresent(Deprecated::class.java) && (m.name != "load") && !m.name
					.contains("$")
			) {
				m.invoke(null)
			}
		}
	}

	fun loadInventoryTest() {
		Reactor.manager!!.registerEventHandler<ReloadableRegistrarEvent<Commands>>(
			LifecycleEvents.COMMANDS,
			LifecycleEventHandler { event: ReloadableRegistrarEvent<Commands>? ->
				val commands = event!!.registrar()
				commands.register(
					Commands.literal("testInv")
						.executes(Command { ctx: CommandContext<CommandSourceStack?>? ->
							setup((ctx!!.getSource()!!.sender as? Player) ?: return@Command 0)
							return@Command Command.SINGLE_SUCCESS
						})
						.build()
				)
			})
	}

	@kotlin.Deprecated("")
	fun loadDataEditor() {
		val manager = Reactor.instance!!.lifecycleManager
		manager.registerEventHandler<ReloadableRegistrarEvent<Commands>>(
			LifecycleEvents.COMMANDS,
			LifecycleEventHandler { event: ReloadableRegistrarEvent<Commands>? ->
				val commands = event!!.registrar()
				commands.register(
					Commands.literal("setData")
						.then(
							Commands.argument<String>("name", StringArgumentType.word()!!)
								.then(
									Commands.argument<String>("value", StringArgumentType.greedyString()!!)
										.executes(Command { ctx: CommandContext<CommandSourceStack?>? ->
											val sender = ctx!!.getSource()!!.sender
											if (sender !is Player) return@Command 0

											val playerData = get(sender)
											val name = StringArgumentType.getString(ctx, "name")
											val value = StringArgumentType.getString(ctx, "value")

											Reactor.Companion.logger!!.info("Hello??")
											Reactor.Companion.logger!!.info(playerData.toString())
											Reactor.Companion.logger!!.info(name)
											Reactor.Companion.logger!!.info(value)
											try {
												PlayerData::class.java.getMethod(name, String::class.java)
													.invoke(playerData, value)
												return@Command Command.SINGLE_SUCCESS
											} catch (e: IllegalAccessException) {
												throw RuntimeException(e)
											} catch (e: NoSuchMethodException) {
												throw RuntimeException(e)
											} catch (e: InvocationTargetException) {
												throw RuntimeException(e)
											}
										})
								)
								.executes { ctx: CommandContext<CommandSourceStack?>? ->
									ctx!!.getSource()!!.sender.sendMessage("Uh, what was that?")
									0
								}
						)
						.executes { ctx: CommandContext<CommandSourceStack?>? ->
							ctx!!.getSource()!!.sender.sendMessage("Uh, what was that?")
							0
						}
						.build(),
					"some bukkit help description string",
					mutableListOf("an-alias")
				)
			})
	}
}
