package com.automafia.bot.handlers

import com.automafia.bot.models.Commands
import com.automafia.bot.models.ResponseObject
import org.telegram.telegrambots.meta.api.objects.Update

class MessageHandler {
    private val commonFunctions: CommonResponseObjectFunctions = CommonResponseObjectFunctions()
    fun handle(update: Update): ResponseObject {
        val message = update.message
        val responseText = if (message.hasText()) {
            when (val messageText = message.text) {
                "/${Commands.START}" -> commonFunctions.welcome(messageText)
                else -> commonFunctions.unknown(messageText)
            }
        } else {
            commonFunctions.unknown(null)
        }
        return responseText
    }
}