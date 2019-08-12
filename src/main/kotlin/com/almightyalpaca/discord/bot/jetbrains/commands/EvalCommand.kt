package com.almightyalpaca.discord.bot.jetbrains.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandEvent
import com.uchuhimo.konf.Config
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import javax.script.ScriptEngineManager
import javax.script.ScriptException

private const val imports = """
    import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.self
    import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.bot
    import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.channel
    import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.guild
    import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.client
    import com.almightyalpaca.discord.bot.jetbrains.commands.EvalCommand.EvalVars.Companion.config
    """

class EvalCommand(private val config: Config) : Command() {
    init {
        name = "eval"
        hidden = true
        guildOnly = true
        ownerCommand = true
    }

    private val manager = ScriptEngineManager()

    override fun execute(event: CommandEvent) {
        val engine = manager.getEngineByExtension("kts")

        EvalVars.self = event.member
        EvalVars.bot = event.selfMember
        EvalVars.channel = event.textChannel
        EvalVars.guild = event.guild
        EvalVars.client = event.client
        EvalVars.config = config

        val startTime = System.nanoTime()

        val result = try {
            engine.eval(imports + event.args)
        } catch (e: ScriptException) {
            e
        }

        val endTime = System.nanoTime()
        val timeUsed = endTime - startTime

        val response = "Executed in ${timeUsed}ns"
        if (result is Exception) {
            result.printStackTrace()

            val cause = result.cause
            if (cause == null)
                event.replyError("$response with ${result.javaClass.simpleName}: ${result.message} on line ${result.stackTrace[0].lineNumber}")
            else
                event.replyError("$response with ${cause.javaClass.simpleName}: ${cause.message} on line ${cause.stackTrace[0].lineNumber}")
        } else if (result != null)
            event.replySuccess("$response , result = $result")
    }

    object EvalVars {
        lateinit var self: Member
        lateinit var bot: Member
        lateinit var channel: TextChannel
        lateinit var guild: Guild
        lateinit var client: CommandClient
        var config: Config = Config()
    }
}
