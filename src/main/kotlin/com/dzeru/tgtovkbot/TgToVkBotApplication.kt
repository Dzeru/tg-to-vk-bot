package com.dzeru.tgtovkbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class TgToVkBotApplication

fun main(args: Array<String>) {
    runApplication<TgToVkBotApplication>(*args)
}
