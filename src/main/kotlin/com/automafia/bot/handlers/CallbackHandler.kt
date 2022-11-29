package com.automafia.bot.handlers

import com.automafia.bot.models.AnswerObject
import com.automafia.bot.models.Commands
import com.automafia.bot.models.ResponseObject
import org.telegram.telegrambots.meta.api.objects.Update

class CallbackHandler {
    private val commonFunctions: CommonResponseObjectFunctions = CommonResponseObjectFunctions()
    fun handle(update: Update): ResponseObject {
        val responseText = if (update.callbackQuery.data.isNotEmpty()) {
            when (val data = update.callbackQuery.data) {
                "/${Commands.START.toString().lowercase()}" -> commonFunctions.welcome(data)
                else -> commonFunctions.unknown(data)
            }
        } else {
            commonFunctions.unknown(null)
        }
        return responseText
    }

}