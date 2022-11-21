package dev.jombi.template

import com.fasterxml.jackson.databind.ObjectMapper
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.jdabuilder.cache
import dev.minn.jda.ktx.jdabuilder.intents
import dev.minn.jda.ktx.jdabuilder.light
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import kotlin.system.measureTimeMillis

suspend fun main() {
    val token = Config.TOKEN
    val prefix = "."
    val mapper = ObjectMapper()

    val jda = light(token, enableCoroutines = true) {
        intents += listOf(*GatewayIntent.values())
        cache += CacheFlag.getPrivileged()
    }

    jda.listener<MessageReceivedEvent> {
        val author = it.author
        val channel = it.channel
        val message = it.message
        val content = message.contentRaw

        if (!content.startsWith(prefix)) return@listener
        if (author.isBot) return@listener

        val spliterator = content.split(" ")
        val command = spliterator[0].drop(prefix.length)
        val args = spliterator.toTypedArray().copyOfRange(1, spliterator.size)

        when (command.lowercase()) {
            "ping" -> {
                val reply: Message
                val milli = measureTimeMillis { reply = message.reply("Pong!").await() }
                reply.editMessage("\uD83C\uDFD3 Pong!\nCalculated Ping: ${milli}ms\nGateway Ping: ${jda.gatewayPing}ms")
                    .await()
            }
        }
    }
}