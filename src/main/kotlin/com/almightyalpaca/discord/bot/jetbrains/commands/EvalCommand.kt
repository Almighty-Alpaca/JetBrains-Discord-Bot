package com.almightyalpaca.discord.bot.jetbrains.commands

import com.almightyalpaca.discord.bot.jetbrains.utils.dummy.CommandClientDummy
import com.almightyalpaca.discord.bot.jetbrains.utils.dummy.GuildDummy
import com.almightyalpaca.discord.bot.jetbrains.utils.dummy.MemberDummy
import com.almightyalpaca.discord.bot.jetbrains.utils.dummy.TextChannelDummy
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandEvent
import com.uchuhimo.konf.Config
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.TextChannel
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class EvalCommand(private val config: Config) : Command()
{
    init
    {
        name = "eval"
        hidden = true
        guildOnly = true
    }

    private val manager = ScriptEngineManager()

    override fun execute(event: CommandEvent)
    {
        if (!event.isOwner)
            return

        val engine = manager.getEngineByExtension("kts")

        EvalVars.self = event.member
        EvalVars.bot = event.selfMember
        EvalVars.channel = event.textChannel
        EvalVars.guild = event.guild
        EvalVars.client = event.client
        EvalVars.config = config

        val startTime = System.nanoTime()

        val result = try
        {
            engine.eval("""
                            import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.self
                            import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.bot
                            import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.channel
                            import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.guild
                            import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.client
                            import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.config
                         """
                        + event.args)
        }
        catch (e: ScriptException)
        {
            e.printStackTrace()

            e
        }

        val endTime = System.nanoTime()
        val timeUsed = endTime - startTime

        val response = "Executed in ${timeUsed}ns"
        if (result is Exception)
        {
            val cause = result.cause
            if (cause == null)
                event.replyError("$response with ${result.javaClass.simpleName}: ${result.message} on line ${result.stackTrace[0].lineNumber}")
            else
                event.replyError("$response with ${cause.javaClass.simpleName}: ${cause.message} on line ${cause.stackTrace[0].lineNumber}")
        }
        else if (result != null)
            event.replySuccess("$response , result = $result")
    }

    class EvalVars
    {
        companion object
        {
            var self: Member = MemberDummy()
            var bot: Member = MemberDummy()
            var channel: TextChannel = TextChannelDummy()
            var guild: Guild = GuildDummy()
            var client: CommandClient = CommandClientDummy()
            var config: Config = Config()
        }
    }
}
