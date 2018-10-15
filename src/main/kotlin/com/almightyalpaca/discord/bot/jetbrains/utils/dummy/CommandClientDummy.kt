package com.almightyalpaca.discord.bot.jetbrains.utils.dummy

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandListener
import com.jagrosh.jdautilities.command.GuildSettingsManager
import net.dv8tion.jda.core.entities.Guild
import java.time.OffsetDateTime
import java.util.concurrent.ScheduledExecutorService
import java.util.function.Function

class CommandClientDummy : CommandClient
{
    override fun getCoOwnerIds(): Array<String>
    {
        throw UnsupportedOperationException()
    }

    override fun applyCooldown(name: String?, seconds: Int)
    {
        throw UnsupportedOperationException()
    }

    override fun <M : GuildSettingsManager<*>?> getSettingsManager(): M
    {
        throw UnsupportedOperationException()
    }

    override fun getError(): String
    {
        throw UnsupportedOperationException()
    }

    override fun addAnnotatedModule(module: Any?)
    {
        throw UnsupportedOperationException()
    }

    override fun addAnnotatedModule(module: Any?, mapFunction: Function<Command, Int>?)
    {
        throw UnsupportedOperationException()
    }

    override fun getServerInvite(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getAltPrefix(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getScheduleExecutor(): ScheduledExecutorService
    {
        throw UnsupportedOperationException()
    }

    override fun cleanCooldowns()
    {
        throw UnsupportedOperationException()
    }

    override fun getWarning(): String
    {
        throw UnsupportedOperationException()
    }

    override fun addCommand(command: Command?)
    {
        throw UnsupportedOperationException()
    }

    override fun addCommand(command: Command?, index: Int)
    {
        throw UnsupportedOperationException()
    }

    override fun <S : Any?> getSettingsFor(guild: Guild?): S
    {
        throw UnsupportedOperationException()
    }

    override fun setListener(listener: CommandListener?)
    {
        throw UnsupportedOperationException()
    }

    override fun getCommands(): MutableList<Command>
    {
        throw UnsupportedOperationException()
    }

    override fun getStartTime(): OffsetDateTime
    {
        throw UnsupportedOperationException()
    }

    override fun getCoOwnerIdsLong(): LongArray
    {
        throw UnsupportedOperationException()
    }

    override fun getListener(): CommandListener
    {
        throw UnsupportedOperationException()
    }

    override fun getSuccess(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getCommandUses(command: Command?): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getCommandUses(name: String?): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getCooldown(name: String?): OffsetDateTime
    {
        throw UnsupportedOperationException()
    }

    override fun getOwnerIdLong(): Long
    {
        throw UnsupportedOperationException()
    }

    override fun getHelpWord(): String
    {
        throw UnsupportedOperationException()
    }

    override fun shutdown()
    {
        throw UnsupportedOperationException()
    }

    override fun getOwnerId(): String
    {
        throw UnsupportedOperationException()
    }

    override fun removeCommand(name: String?)
    {
        throw UnsupportedOperationException()
    }

    override fun usesLinkedDeletion(): Boolean
    {
        throw UnsupportedOperationException()
    }

    override fun getPrefix(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getTextualPrefix(): String
    {
        throw UnsupportedOperationException()
    }

    override fun getTotalGuilds(): Int
    {
        throw UnsupportedOperationException()
    }

    override fun getRemainingCooldown(name: String?): Int
    {
        throw UnsupportedOperationException()
    }

}
