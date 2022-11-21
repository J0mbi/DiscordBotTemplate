package dev.jombi.template

import com.fasterxml.jackson.databind.ObjectMapper
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.events.onCommand
import dev.minn.jda.ktx.interactions.commands.Command
import dev.minn.jda.ktx.jdabuilder.cache
import dev.minn.jda.ktx.jdabuilder.intents
import dev.minn.jda.ktx.jdabuilder.light
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import kotlin.system.measureTimeMillis

suspend fun main() {
    val token = Config.TOKEN
    val mapper = ObjectMapper()

    val jda = light(token, enableCoroutines = true) {
        intents += listOf(*GatewayIntent.values())
        cache += CacheFlag.getPrivileged()
    }

    jda.updateCommands().addCommands(
        Command("ping", "Pong!")
    )

    jda.onCommand("ping") {
        val reply: InteractionHook
        val milli = measureTimeMillis { reply = it.reply("Pong!").await() }
        reply.editOriginal("\uD83C\uDFD3 Pong!\nCalculated Ping: ${milli}ms\nGateway Ping: ${jda.gatewayPing}ms")
            .await()
    }
}