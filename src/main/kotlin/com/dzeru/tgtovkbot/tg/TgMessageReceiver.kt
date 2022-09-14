package com.dzeru.tgtovkbot.tg

import com.dzeru.tgtovkbot.properties.BotProperties
import com.dzeru.tgtovkbot.vk.VkMessageSender
import org.apache.logging.log4j.LogManager.getLogger
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Service
class TgMessageReceiver(val vkMessageSender: VkMessageSender, val botProperties: BotProperties) :
    TelegramLongPollingBot() {

    override fun onUpdateReceived(update: Update) {
        try {
            logger.info("TG Message: $update")
            val text = update.message.text ?: update.message.caption
            val fromUserName = if (null == update.message.from.userName) "" else update.message.from.userName
            val fromFirstName = update.message.from.firstName
            val fromLastName = if (null == update.message.from.lastName) "" else update.message.from.lastName
            vkMessageSender.send(text, fromUserName, fromFirstName, fromLastName)
        } catch (e: TelegramApiException) {
            logger.error("TG API Exception: $e")
        } catch (e: Exception) {
            logger.error("Another TG Exception: $e")
        }
    }

    override fun getBotUsername(): String {
        return botProperties.tg.name
    }

    override fun getBotToken(): String {
        return botProperties.tg.token
    }

    companion object {
        private val logger = getLogger(TgMessageReceiver)
    }
}
