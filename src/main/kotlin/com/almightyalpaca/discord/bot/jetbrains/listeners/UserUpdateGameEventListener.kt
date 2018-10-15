package com.almightyalpaca.discord.bot.jetbrains.listeners

import com.almightyalpaca.discord.bot.jetbrains.settings.Bot
import com.almightyalpaca.discord.bot.jetbrains.settings.Guild
import com.uchuhimo.konf.Config
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.RichPresence
import net.dv8tion.jda.core.events.guild.GuildReadyEvent
import net.dv8tion.jda.core.events.user.update.UserUpdateGameEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.util.*

class UserUpdateGameEventListener(private val config: Config) : ListenerAdapter()
{
    override fun onUserUpdateGame(event: UserUpdateGameEvent)
    {
        if (config[Bot.development])
            return

        if (event.guild.idLong == config[Guild.id])
            checkMember(event.member)
    }

    override fun onGuildReady(event: GuildReadyEvent)
    {
        if (config[Bot.development])
            return

        if (event.guild.idLong == config[Guild.id])
            for (member in event.guild.memberCache)
                checkMember(member)
    }

    private fun checkMember(member: Member)
    {
        val game = member.game as? RichPresence ?: return
        val guild = member.guild

        val fanclub = Collections.singleton(guild.getRoleById(config[Guild.Roles.fanclub]))
        val traitor = Collections.singleton(guild.getRoleById(config[Guild.Roles.traitor]))

        val fanclubApps = config[Guild.Applications.fanclub]
        val traitorApps = config[Guild.Applications.traitor]

        val isFanclubApp = fanclubApps.contains(game.applicationIdLong)
        val isTraitorApp = traitorApps.contains(game.applicationIdLong)

        if (isFanclubApp)
            guild.controller.modifyMemberRoles(member, fanclub, traitor).queue()
        else if (isTraitorApp)
            guild.controller.modifyMemberRoles(member, traitor, fanclub).queue()
    }
}
