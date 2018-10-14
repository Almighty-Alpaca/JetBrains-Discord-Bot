package com.almightyalpaca.discord.bot.jetbrains.utils

import com.jagrosh.jdautilities.command.CommandClientBuilder

inline infix fun <reified T : CommandClientBuilder> T.setOwnerId(ownerId: Long?): T
{
    return this.setOwnerId(ownerId?.toString()) as T
}

inline infix fun <reified T : CommandClientBuilder> T.setCoOwnerIds(coOwnerIds: Set<Long>): T
{
    return this.setCoOwnerIds(*coOwnerIds.map { it.toString() }.toTypedArray()) as T
}
