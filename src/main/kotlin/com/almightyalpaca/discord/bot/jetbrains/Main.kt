package com.almightyalpaca.discord.bot.jetbrains

import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand
import com.almightyalpaca.discord.bot.jetbrains.commands.PingCommand
import com.almightyalpaca.discord.bot.jetbrains.commands.ShutdownCommand
import com.almightyalpaca.discord.bot.jetbrains.listeners.UserActivityStartListener
import com.almightyalpaca.discord.bot.jetbrains.utils.setCoOwnerIds
import com.almightyalpaca.discord.bot.jetbrains.utils.setOwnerId
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.uchuhimo.konf.Config
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Paths

val isDocker by lazy { System.getenv("DOCKER") != null }

fun main() {
    val configFolder = when {
        isDocker -> Paths.get("/config")
        else -> Paths.get(".")
    }

    val config = Config {
        addSpec(Settings)
    }
        .from.yaml.file(configFolder.resolve("config.yaml").toFile())
        .from.env()
        .from.systemProperties()

    val client = CommandClientBuilder()
        .setOwnerId(config[Settings.owner])
        .setPrefix(config[Settings.command_prefix])
        .setCoOwnerIds(config[Settings.co_owners])
        // .setGame(Game.of(config[Bot.Status.type], config[Bot.Status.name], config[Bot.Status.url]))
        .setActivity(Activity.playing("with roles"))
        // .setServerInvite(config[Guild.invite])
        .addCommand(EvalCommand(config))
        .addCommand(PingCommand())
        .addCommand(ShutdownCommand())
        .setShutdownAutomatically(true)
        .build()

    JDABuilder()
        .setToken(config[Settings.token])
        .addEventListeners(client)
        .addEventListeners(UserActivityStartListener(config))
        .build()
}
