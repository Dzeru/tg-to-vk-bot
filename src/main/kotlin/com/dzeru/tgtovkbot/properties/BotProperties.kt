package com.dzeru.tgtovkbot.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "bot")
data class BotProperties(
    val vk: VkProperties,
    val tg: TgProperties
)
