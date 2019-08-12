package com.almightyalpaca.discord.bot.jetbrains.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class ShutdownCommand : Command() {
    init {
        name = "shutdown"
        hidden = true
        ownerCommand = true
    }

    override fun execute(event: CommandEvent) {
        event.jda.shutdown()

        with(event.jda.httpClient) {
            connectionPool().evictAll() // Remove once https://github.com/square/okhttp/issues/4029 has been fixed
            dispatcher().executorService().shutdown()
        }
    }
}
