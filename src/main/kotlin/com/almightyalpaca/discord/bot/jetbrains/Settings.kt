package com.almightyalpaca.discord.bot.jetbrains

import com.uchuhimo.konf.ConfigSpec
import java.util.*

object Settings : ConfigSpec("") {
    val token by required<String>()

    val owner by required<Long>()
    val co_owners by optional<Set<Long>>(Collections.emptySet())

    val command_prefix by required<String>()

    val development by optional(false)

    val guilds by required<Set<Guild>>()

    data class Guild(val name: String? = null, val id: Long, val groups: Set<Group>) {
        data class Group(val name: String? = null, val roles: Set<Role>) {
            val allRoleIds = roles.map { it.id }

            data class Role(val name: String? = null, val id: Long, val triggers: Set<Long>)
        }
    }
}
