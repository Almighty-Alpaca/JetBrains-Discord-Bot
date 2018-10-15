package com.almightyalpaca.discord.bot.jetbrains.utils.dummy

import net.dv8tion.jda.client.requests.restaction.pagination.MentionPaginationAction
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.Region
import net.dv8tion.jda.core.entities.*
import net.dv8tion.jda.core.managers.AudioManager
import net.dv8tion.jda.core.managers.GuildController
import net.dv8tion.jda.core.managers.GuildManager
import net.dv8tion.jda.core.requests.RestAction
import net.dv8tion.jda.core.requests.restaction.MemberAction
import net.dv8tion.jda.core.requests.restaction.pagination.AuditLogPaginationAction
import net.dv8tion.jda.core.utils.cache.MemberCacheView
import net.dv8tion.jda.core.utils.cache.SnowflakeCacheView
import java.util.*

class GuildDummy : Guild
{
    override fun getRoleCache(): SnowflakeCacheView<Role>
    {
        throw UnsupportedOperationException()
    }

    override fun getVerificationLevel(): Guild.VerificationLevel
    {
        throw UnsupportedOperationException()
    }

    override fun addMember(accessToken: String?, userId: String?): MemberAction
    {
        throw UnsupportedOperationException()
    }

    override fun getController(): GuildController
    {
        throw UnsupportedOperationException()
    }

    override fun getWebhooks(): RestAction<MutableList<Webhook>>
    {
        throw UnsupportedOperationException()
    }

    override fun getMemberCache(): MemberCacheView
    {
        throw UnsupportedOperationException()
    }

    override fun retrieveEmoteById(id: String): RestAction<ListedEmote>
    {
        throw UnsupportedOperationException()
    }

    override fun getBanList(): RestAction<MutableList<Guild.Ban>>
    {
        throw UnsupportedOperationException()
    }

    override fun getPublicRole(): Role
    {
        throw UnsupportedOperationException()
    }

    override fun getAuditLogs(): AuditLogPaginationAction
    {
        throw UnsupportedOperationException()
    }

    override fun getEmoteCache(): SnowflakeCacheView<Emote>
    {
        throw UnsupportedOperationException()
    }

    override fun getRequiredMFALevel(): Guild.MFALevel
    {
        throw UnsupportedOperationException()
    }

    override fun isMember(user: User?): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun leave(): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun getOwnerIdLong(): Long
    {
        throw UnsupportedOperationException()
    }

    override fun getRecentMentions(): MentionPaginationAction
    {
        throw UnsupportedOperationException()
    }

    override fun getIconId(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getMember(user: User?): Member
    {
        throw UnsupportedOperationException()
    }

    override fun getVoiceStates(): MutableList<GuildVoiceState>
    {
        throw UnsupportedOperationException()
    }

    override fun getIdLong(): Long
    {
        throw UnsupportedOperationException()
    }

    override fun retrieveEmotes(): RestAction<MutableList<ListedEmote>>
    {
        throw UnsupportedOperationException()
    }

    override fun getCategoryCache(): SnowflakeCacheView<Category>
    {
        throw UnsupportedOperationException()
    }

    override fun getAfkChannel(): VoiceChannel
    {
        throw UnsupportedOperationException()
    }

    override fun getAudioManager(): AudioManager
    {
        throw UnsupportedOperationException()
    }

    override fun getExplicitContentLevel(): Guild.ExplicitContentLevel
    {
        throw UnsupportedOperationException()
    }

    override fun retrieveRegions(includeDeprecated: Boolean): RestAction<EnumSet<Region>>
    {
        throw UnsupportedOperationException()
    }

    override fun getName(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getTextChannelCache(): SnowflakeCacheView<TextChannel>
    {
        throw UnsupportedOperationException()
    }

    override fun checkVerification(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getBanById(userId: String): RestAction<Guild.Ban>
    {
        throw UnsupportedOperationException()
    }

    override fun getVanityUrl(): RestAction<String>
    {
        throw UnsupportedOperationException()
    }

    override fun getInvites(): RestAction<MutableList<Invite>>
    {
        throw UnsupportedOperationException()
    }

    override fun getSystemChannel(): TextChannel
    {
        throw UnsupportedOperationException()
    }

    override fun getVoiceChannelCache(): SnowflakeCacheView<VoiceChannel>
    {
        throw UnsupportedOperationException()
    }

    override fun getDefaultChannel(): TextChannel?
    {
        throw UnsupportedOperationException()
    }

    override fun getPrunableMemberCount(days: Int): RestAction<Int>
    {
        throw UnsupportedOperationException()
    }

    override fun getManager(): GuildManager
    {
        throw UnsupportedOperationException()
    }

    override fun getIconUrl(): String
    {
        throw UnsupportedOperationException()
    }

    override fun delete(): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun delete(mfaCode: String?): RestAction<Void>
    {
        throw UnsupportedOperationException()
    }

    override fun getOwner(): Member
    {
        throw UnsupportedOperationException()
    }

    override fun getSplashUrl(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getDefaultNotificationLevel(): Guild.NotificationLevel
    {
        throw UnsupportedOperationException()
    }

    override fun getSelfMember(): Member
    {
        throw UnsupportedOperationException()
    }

    override fun getJDA(): JDA
    {
        throw UnsupportedOperationException()
    }

    override fun getFeatures(): MutableSet<String>
    {
        throw UnsupportedOperationException()
    }

    override fun getSplashId(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getRegionRaw(): String
    {
        throw UnsupportedOperationException()
    }

    override fun isAvailable(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getAfkTimeout(): Guild.Timeout
    {
        throw UnsupportedOperationException()
    }
}
