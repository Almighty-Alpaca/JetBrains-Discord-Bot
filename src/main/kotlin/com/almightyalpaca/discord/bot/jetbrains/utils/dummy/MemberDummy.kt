package com.almightyalpaca.discord.bot.jetbrains.utils.dummy

import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.entities.*
import java.awt.Color
import java.time.OffsetDateTime

class MemberDummy : Member
{
    override fun isOwner(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getEffectiveName(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getColor(): Color
    {
        throw UnsupportedOperationException()
    }

    override fun getJoinDate(): OffsetDateTime
    {
        throw UnsupportedOperationException()
    }

    override fun getOnlineStatus(): OnlineStatus
    {
        throw UnsupportedOperationException()
    }

    override fun getPermissions(channel: Channel?): MutableList<Permission>
    {
        throw UnsupportedOperationException()
    }

    override fun getPermissions(): MutableList<Permission>
    {
        throw UnsupportedOperationException()
    }

    override fun getVoiceState(): GuildVoiceState
    {
        throw UnsupportedOperationException()
    }

    override fun getGame(): Game
    {
        throw UnsupportedOperationException()
    }

    override fun getJDA(): JDA
    {
        throw UnsupportedOperationException()
    }

    override fun canInteract(member: Member?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun canInteract(role: Role?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun canInteract(emote: Emote?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getAsMention(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getDefaultChannel(): TextChannel?
    {
        throw UnsupportedOperationException()
    }

    override fun getNickname(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getRoles(): MutableList<Role>
    {
        throw UnsupportedOperationException()
    }

    override fun getUser(): User
    {
        throw UnsupportedOperationException()
    }

    override fun getColorRaw(): Int
    {
        throw UnsupportedOperationException()
    }

    override fun hasPermission(vararg permissions: Permission?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun hasPermission(permissions: MutableCollection<Permission>?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun hasPermission(channel: Channel?, vararg permissions: Permission?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun hasPermission(channel: Channel?, permissions: MutableCollection<Permission>?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getGuild(): Guild
    {
        throw UnsupportedOperationException()
    }
}
