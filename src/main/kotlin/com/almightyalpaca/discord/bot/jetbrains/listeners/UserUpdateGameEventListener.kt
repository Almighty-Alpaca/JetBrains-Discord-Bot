package com.almightyalpaca.discord.bot.jetbrains.listeners

import com.almightyalpaca.discord.bot.jetbrains.settings.Guild
import com.uchuhimo.konf.Config
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.RichPresence
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.user.update.UserUpdateGameEvent
import net.dv8tion.jda.core.hooks.EventListener
import java.util.*

class UserUpdateGameEventListener(private val config: Config) : EventListener
{
    override fun onEvent(event: Event?)
    {
        if (event is UserUpdateGameEvent)
        {

            if (event.guild.idLong == config[Guild.id])
                checkMember(event.member)
        }
        else if (event is ReadyEvent)
        {
            val guild = event.jda.getGuildById(config[Guild.id])

            if (guild != null)
                for (member in guild.memberCache)
                    checkMember(member)
        }
    }

    private fun checkMember(member: Member)
    {
        val game = member.game as? RichPresence ?: return
        val guild = member.guild

        val fanclub = Collections.singleton(guild.getRoleById(config[Guild.Roles.fanclub]))
        val traitor = Collections.singleton(guild.getRoleById(config[Guild.Roles.traitor]))

        if (config[Guild.Applications.fanclub].contains(game.applicationIdLong))
            guild.controller.modifyMemberRoles(member, fanclub, traitor).queue()
        else if (config[Guild.Applications.traitor].contains(game.applicationIdLong))
            guild.controller.modifyMemberRoles(member, traitor, fanclub).queue()
    }
}
