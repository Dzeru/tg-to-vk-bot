package com.dzeru.tgtovkbot.vk

import com.dzeru.tgtovkbot.properties.BotProperties
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.apache.logging.log4j.LogManager.getLogger
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class VkMessageSender(val botProperties: BotProperties) {

    fun send(text: String, fromUserName: String, fromFirstName: String, fromLastName: String) {
        var startIndex = 0
        var message = "От $fromFirstName $fromLastName $fromUserName:\n\n$text"
        logger.info("VK Message: $message")
        var lastSpaceIndex = 0
        while (startIndex < text.length) {
            if (text.length - startIndex <= VK_LENGTH) {
                message = message.substring(startIndex)
            } else {
                lastSpaceIndex = text.lastIndexOf(' ', startIndex + VK_LENGTH)
                message = message.substring(startIndex, lastSpaceIndex)
            }
            val postParameters = listOf(
                BasicNameValuePair(ACCESS_TOKEN, botProperties.vk.token),
                BasicNameValuePair(VERSION, botProperties.vk.apiVersion),
                BasicNameValuePair(PEER_ID, botProperties.vk.targetChatId),
                BasicNameValuePair(RANDOM_ID, random.nextInt().toString()),
                BasicNameValuePair(MESSAGE, message)
            )

            val postRequest = HttpPost(VK_API_SEND_MESSAGE_URL)
            postRequest.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            postRequest.entity = UrlEncodedFormEntity(postParameters, StandardCharsets.UTF_8)
            val client = HttpClientBuilder.create().build()
            val response = client.execute(postRequest)

            if (lastSpaceIndex > 0) {
                startIndex = lastSpaceIndex + 1
                lastSpaceIndex = 0
            } else {
                startIndex += VK_LENGTH
            }
            logger.info("VK Response: $response")
        }
    }

    companion object {
        private val logger = getLogger(VkMessageSender)
        private val random = Random()
        private const val VK_LENGTH = 4096
        private const val VK_API_SEND_MESSAGE_URL = "https://api.vk.com/method/messages.send"
        private const val ACCESS_TOKEN = "access_token"
        private const val VERSION = "v"
        private const val PEER_ID = "peer_id"
        private const val RANDOM_ID = "random_id"
        private const val MESSAGE = "message"
    }
}
