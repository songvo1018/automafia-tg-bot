package com.automafia.bot.handlers

import com.automafia.bot.handlers.scenarios.Creating
import com.automafia.bot.models.Commands
import com.automafia.bot.models.ResponseObject
import org.telegram.telegrambots.meta.api.objects.Update

class CallbackHandler {
    private val commonFunctions: CommonResponseObjectFunctions = CommonResponseObjectFunctions()
    private val creatingScenarios: Creating = Creating()
    fun handle(update: Update): ResponseObject {
        val responseText = if (update.callbackQuery.data.isNotEmpty()) {
            println("callback handler")
            println("user chose: ${update.callbackQuery.data}")
            val userName = update.callbackQuery.from.userName
            val userInput = update.callbackQuery.data.uppercase()

            if(userInput.contains(Commands.SELECT.toString())) {
                return creatingScenarios.selectedConfig(userInput, userName)
            }

            commonFunctions.handleMethodResolver(userInput)
        } else {
            commonFunctions.unknown(null)
        }
        return responseText
    }

}