package com.almightyalpaca.discord.bot.jetbrains.listeners

import com.almightyalpaca.discord.bot.jetbrains.settings.Bot
import com.almightyalpaca.discord.bot.jetbrains.settings.Guild
import com.uchuhimo.konf.Config
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.RichPresence
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

class UserUpdateGameEventListener(private val config: Config) : ListenerAdapter() {
    override fun onUserActivityStart(event: UserActivityStartEvent) {
        if (config[Bot.development])
            return

        if (event.guild.idLong == config[Guild.id])
            checkActivity(event.member, event.newActivity)
    }

    override fun onGuildReady(event: GuildReadyEvent) {
        if (config[Bot.development])
            return

        if (event.guild.idLong == config[Guild.id])
            for (member in event.guild.memberCache)
                for (activity in member.activities)
                    checkActivity(member, activity)
    }

    private fun checkActivity(member: Member, activity: Activity) {
        if (activity !is RichPresence)
            return

        val guild = member.guild

        val fanclub = Collections.singleton(guild.getRoleById(config[Guild.Roles.fanclub]))
        val traitor = Collections.singleton(guild.getRoleById(config[Guild.Roles.traitor]))

        val fanclubApps = config[Guild.Applications.fanclub]
        val traitorApps = config[Guild.Applications.traitor]

        val isFanclubApp = fanclubApps.contains(activity.applicationIdLong)
        val isTraitorApp = traitorApps.contains(activity.applicationIdLong)

        if (isFanclubApp)
            guild.modifyMemberRoles(member, fanclub, traitor).queue()
        else if (isTraitorApp)
            guild.modifyMemberRoles(member, traitor, fanclub).queue()
    }
}
