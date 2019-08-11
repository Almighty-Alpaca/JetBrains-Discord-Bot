package com.almightyalpaca.discord.bot.jetbrains.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class PingCommand : Command() {
    init {
        name = "ping"
        hidden = true
        guildOnly = false
    }

    override fun execute(event: CommandEvent) = event.reply("pong")
}
