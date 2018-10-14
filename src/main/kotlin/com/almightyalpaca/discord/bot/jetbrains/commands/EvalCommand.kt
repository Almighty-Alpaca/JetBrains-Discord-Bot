package com.almightyalpaca.discord.bot.jetbrains.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.uchuhimo.konf.Config
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class EvalCommand(private val config: Config) : Command()
{

    init
    {
        name = "eval"
        hidden = true
        guildOnly = false
    }

    private val manager = ScriptEngineManager()

    override fun execute(event: CommandEvent)
    {
        if (!event.isOwner)
            return

        val engine = manager.getEngineByExtension("kts")

        engine.put("self", event.author)
        engine.put("bot", event.selfUser)
        engine.put("channel", event.channel)
        engine.put("client", event.client)
        engine.put("config", config)

        if (event.channelType.isGuild)
        {
            engine.put("selfMember", event.member)
            engine.put("botMember", event.selfMember)
            engine.put("guild", event.guild)
        }

        val startTime = System.nanoTime()

        val result = try
        {
            engine.eval(event.args)
        }
        catch (e: ScriptException)
        {
            e
        }

        val endTime = System.nanoTime()
        val timeUsed = endTime - startTime

        val response = "Executed in ${timeUsed}ns"
        if (result is Exception)
            event.replyError("$response with ${result.cause?.javaClass?.simpleName}: ${result.cause?.message} on line ${result.stackTrace[0].lineNumber}")
        else if (result != null)
            event.replySuccess("$response , result = $result")
    }
}
