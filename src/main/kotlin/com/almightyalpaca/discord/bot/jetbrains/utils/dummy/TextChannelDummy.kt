package com.almightyalpaca.discord.bot.jetbrains.utils.dummy

import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.*
import net.dv8tion.jda.core.managers.ChannelManager
import net.dv8tion.jda.core.requests.RestAction
import net.dv8tion.jda.core.requests.restaction.*

class TextChannelDummy : TextChannel
{
    override fun deleteMessages(messages: MutableCollection<Message>?): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun createCopy(guild: Guild?): ChannelAction
    {
        throw UnsupportedOperationException()
    }

    override fun putPermissionOverride(member: Member?): PermissionOverrideAction
    {
        throw UnsupportedOperationException()
    }

    override fun putPermissionOverride(role: Role?): PermissionOverrideAction
    {
        throw UnsupportedOperationException()
    }

    override fun getWebhooks(): RestAction<MutableList<Webhook>>
    {
        throw UnsupportedOperationException()
    }

    override fun deleteMessagesByIds(messageIds: MutableCollection<String>?): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun getMemberPermissionOverrides(): MutableList<PermissionOverride>
    {
        throw UnsupportedOperationException()
    }

    override fun isNSFW(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getGuild(): Guild
    {
        throw UnsupportedOperationException()
    }

    override fun removeReactionById(messageId: String?, unicode: String?, user: User?): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun createWebhook(name: String?): WebhookAction
    {
        throw UnsupportedOperationException()
    }

    override fun createPermissionOverride(member: Member?): PermissionOverrideAction
    {
        throw UnsupportedOperationException()
    }

    override fun createPermissionOverride(role: Role?): PermissionOverrideAction
    {
        throw UnsupportedOperationException()
    }

    override fun getIdLong(): Long
    {
        throw UnsupportedOperationException()
    }

    override fun getAsMention(): String
    {
        throw UnsupportedOperationException()
    }

    override fun deleteWebhookById(id: String?): AuditableRestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun getPermissionOverride(member: Member?): PermissionOverride
    {
        throw UnsupportedOperationException()
    }

    override fun getPermissionOverride(role: Role?): PermissionOverride
    {
        throw UnsupportedOperationException()
    }

    override fun canTalk(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun canTalk(member: Member?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun clearReactionsById(messageId: String?): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun createInvite(): InviteAction
    {
        throw UnsupportedOperationException()
    }

    override fun getName(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getRolePermissionOverrides(): MutableList<PermissionOverride>
    {
        throw UnsupportedOperationException()
    }

    override fun getInvites(): RestAction<MutableList<Invite>>
    {
        throw UnsupportedOperationException()
    }

    override fun getMembers(): MutableList<Member>
    {
        throw UnsupportedOperationException()
    }

    override fun getManager(): ChannelManager
    {
        throw UnsupportedOperationException()
    }

    override fun delete(): AuditableRestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun getParent(): Category
    {
        throw UnsupportedOperationException()
    }

    override fun getPermissionOverrides(): MutableList<PermissionOverride>
    {
        throw UnsupportedOperationException()
    }

    override fun hasLatestMessage(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getSlowmode(): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getType(): ChannelType
    {
        throw UnsupportedOperationException()
    }

    override fun compareTo(other: TextChannel?): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getPosition(): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getPositionRaw(): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getJDA(): JDA
    {
        throw UnsupportedOperationException()
    }

    override fun getLatestMessageIdLong(): Long
    {
        throw UnsupportedOperationException()
    }

    override fun getTopic(): String
    {
        throw UnsupportedOperationException()
    }
}
