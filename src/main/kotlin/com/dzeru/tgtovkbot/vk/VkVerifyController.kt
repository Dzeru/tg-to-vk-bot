package com.dzeru.tgtovkbot.vk

import com.dzeru.tgtovkbot.properties.BotProperties
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class VkVerifyController(val botProperties: BotProperties, val vkMessageSender: VkMessageSender) {

    @PostMapping(value = ["\${bot.vk.url}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun csitTgVk(@RequestBody incomingMessage: String): String {
        return if (incomingMessage.contains(CONFIRMATION)) {
            botProperties.vk.confirmationCode
        } else {
            OK
        }
    }

    companion object {
        private const val CONFIRMATION = "confirmation"
        private const val OK = "ok"
    }
}
