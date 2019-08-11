package com.almightyalpaca.discord.bot.jetbrains.settings

import com.uchuhimo.konf.ConfigSpec

object Guild : ConfigSpec("guild") {
    val id by required<Long>()
    val invite by optional<String?>(null)

    object Applications : ConfigSpec("applications") {
        val fanclub by required<Set<Long>>()
        val traitor by required<Set<Long>>()
    }

    object Roles : ConfigSpec("roles") {
        val fanclub by required<Long>()
        val traitor by required<Long>()
    }
}
