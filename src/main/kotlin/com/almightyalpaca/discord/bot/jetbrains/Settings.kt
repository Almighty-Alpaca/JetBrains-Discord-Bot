package com.almightyalpaca.discord.bot.jetbrains

import com.uchuhimo.konf.ConfigSpec
import net.dv8tion.jda.api.entities.RichPresence
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

            data class Role(val name: String? = null, val id: Long, val triggers: Trigger?) {
                data class Trigger(
                    val appIds: Set<Long> = setOf(),
                    val states: Set<String> = setOf(),
                    val details: Set<String> = setOf(),
                    val partyIds: Set<String> = setOf(),
                    val largeImageKeys: Set<String> = setOf(),
                    val largeImageTexts: Set<String> = setOf(),
                    val smallImageKeys: Set<String> = setOf(),
                    val smallImageTexts: Set<String> = setOf()
                ) {
                    fun matches(presence: RichPresence): Boolean {
                        return presence.applicationIdLong in appIds
                                || presence.state in states
                                || presence.details in details
                                || presence.party?.id in partyIds
                                || presence.largeImage?.key in largeImageKeys
                                || presence.largeImage?.text in largeImageTexts
                                || presence.smallImage?.key in smallImageKeys
                                || presence.smallImage?.text in smallImageTexts
                    }
                }
            }
        }
    }
}
