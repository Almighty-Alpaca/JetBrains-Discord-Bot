package com.almightyalpaca.discord.bot.jetbrains.listeners

import com.almightyalpaca.discord.bot.jetbrains.Settings
import com.almightyalpaca.discord.bot.jetbrains.utils.modifyRoles
import com.uchuhimo.konf.Config
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.RichPresence
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class UserActivityStartListener(private val config: Config) : ListenerAdapter() {
    override fun onUserActivityStart(event: UserActivityStartEvent) {
        if (config[Settings.development])
            return

        checkActivity(event.member, event.newActivity)
    }

    override fun onGuildReady(event: GuildReadyEvent) {
        if (config[Settings.development])
            return

        for (member in event.guild.memberCache)
            for (activity in member.activities)
                checkActivity(member, activity)
    }

    private fun checkActivity(member: Member, activity: Activity) {
        if (activity !is RichPresence)
            return

        val guild = config[Settings.guilds]
            .asSequence()
            .find { it.id == member.guild.idLong }
            ?: return

        for (group in guild.groups) {
            for (role in group.roles) {
                if (activity.applicationIdLong in role.triggers) {
                    member.modifyRoles(rolesToAdd = listOf(role.id), rolesToRemove = group.allRoleIds - role.id).queue()
                }
            }
        }
    }
}
