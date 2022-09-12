package com.dzeru.tgtovkbot.properties

data class VkProperties(
    val token: String,
    val secret: String,
    val confirmationCode: String,
    val apiVersion: String,
    val url: String,
    val targetChatId: String
)
