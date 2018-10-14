package com.almightyalpaca.discord.bot.jetbrains.settings

import com.uchuhimo.konf.ConfigSpec
import net.dv8tion.jda.core.entities.Game
import java.util.*

class Bot
{
    companion object : ConfigSpec("bot")
    {
        val token by required<String>()

        val owner by required<Long>()
        val co_owners by optional<Set<Long>>(Collections.emptySet())

        val command_prefix by required<String>()
    }

    class Status
    {
        companion object : ConfigSpec("bot.status")
        {
            val type by Guild.Applications.optional(Game.GameType.DEFAULT)
            val name by Guild.Applications.optional<String?>(null)
            val url by Guild.Applications.optional<String?>(null)
        }
    }
}
