package com.almightyalpaca.discord.bot.jetbrains.utils

import net.dv8tion.jda.core.entities.Game

fun Game.of(type: Game.GameType? = null, name: String? = null, url: String? = null): Game?
{
    return when (type)
    {
        Game.GameType.STREAMING ->
            return when (url)
            {
                null -> null
                else -> Game.streaming(name, url)
            }
        null -> null
        else ->
        {
            return Game.of(type, name)
        }
    }
}

