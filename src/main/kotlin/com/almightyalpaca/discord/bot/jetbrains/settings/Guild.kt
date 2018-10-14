package com.almightyalpaca.discord.bot.jetbrains.settings

import com.uchuhimo.konf.ConfigSpec

class Guild
{

    companion object : ConfigSpec("guild")
    {
        val id by required<Long>()
        val invite by optional<String?>(null)
    }

    class Applications
    {
        companion object : ConfigSpec("guild.applications")
        {
            val fanclub by required<Set<Long>>()
            val traitor by required<Set<Long>>()
        }
    }

    class Roles
    {
        companion object : ConfigSpec("guild.roles")
        {
            val fanclub by required<Long>()
            val traitor by required<Long>()
        }
    }
}
