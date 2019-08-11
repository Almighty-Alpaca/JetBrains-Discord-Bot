package com.almightyalpaca.discord.bot.jetbrains

import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand
import com.almightyalpaca.discord.bot.jetbrains.commands.PingCommand
import com.almightyalpaca.discord.bot.jetbrains.listeners.UserUpdateGameEventListener
import com.almightyalpaca.discord.bot.jetbrains.settings.Bot
import com.almightyalpaca.discord.bot.jetbrains.settings.Guild
import com.almightyalpaca.discord.bot.jetbrains.utils.setCoOwnerIds
import com.almightyalpaca.discord.bot.jetbrains.utils.setOwnerId
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.uchuhimo.konf.Config
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Paths

val isDocker by lazy { System.getenv("DOCKER") != null }

fun main() {
    val configFolder = if (isDocker)
        Paths.get("/config")
    else
        Paths.get(".")

    val config = Config {
        addSpec(Bot)
        addSpec(Guild)
    }
        .from.yaml.file(configFolder.resolve("config.yaml").toFile())
        .from.env()
        .from.systemProperties()

    val client = CommandClientBuilder()
        .setOwnerId(config[Bot.owner])
        .setPrefix(config[Bot.command_prefix])
        .setCoOwnerIds(config[Bot.co_owners])
        // .setGame(Game.of(config[Bot.Status.type], config[Bot.Status.name], config[Bot.Status.url]))
        .setActivity(Activity.playing("with roles"))
        .setServerInvite(config[Guild.invite])
        .addCommand(EvalCommand(config))
        .addCommand(PingCommand())
        .build()

    JDABuilder()
        .setToken(config[Bot.token])
        .addEventListeners(client)
        .addEventListeners(UserUpdateGameEventListener(config))
        .build()
}
