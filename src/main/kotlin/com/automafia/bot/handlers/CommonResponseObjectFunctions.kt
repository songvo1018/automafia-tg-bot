package com.automafia.bot.handlers

import com.automafia.bot.models.AnswerObject
import com.automafia.bot.models.Commands
import com.automafia.bot.models.ResponseObject

class CommonResponseObjectFunctions {

    fun welcome(userInput: String): ResponseObject {
        return ResponseObject(
            "You chose $userInput",
            "",
            listOf(
                AnswerObject("Создать игру", "/${Commands.CREATE.toString().lowercase()}"),
                AnswerObject("Присоединиться", "/${Commands.CONNECT.toString().lowercase()}"),
                AnswerObject("Оставить отзыв", "/${Commands.REVIEW.toString().lowercase()}")
            )
        )
    }

    fun unknown(userInput: String?): ResponseObject {
        return ResponseObject(
            "Неизвестная команда: $userInput",
            "Верниуться к началу",
            listOf(AnswerObject("Вернуться к началу", "/start"))
        )
    }
}