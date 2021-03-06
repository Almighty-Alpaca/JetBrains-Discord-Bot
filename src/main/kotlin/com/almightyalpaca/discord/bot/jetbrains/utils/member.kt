package com.almightyalpaca.discord.bot.jetbrains.utils

import net.dv8tion.jda.api.entities.Member

fun Member.modifyRoles(rolesToAdd: Collection<Long>, rolesToRemove: Collection<Long>) =
    guild.modifyMemberRoles(this, rolesToAdd.map { requireNotNull(guild.getRoleById(it)) }, rolesToRemove.map { requireNotNull(guild.getRoleById(it)) })
