package com.almightyalpaca.discord.bot.jetbrains.utils

import net.dv8tion.jda.api.entities.Activity

fun Activity.of(type: Activity.ActivityType? = null, name: String, url: String? = null): Activity? {
    return when (type) {
        Activity.ActivityType.STREAMING ->
            when (url) {
                null -> null
                else -> Activity.streaming(name, url)
            }
        null -> null
        else -> Activity.of(type, name)

    }
}

