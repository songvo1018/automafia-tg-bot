package com.automafia.bot.handlers

import com.automafia.bot.handlers.scenarios.Creating
import com.automafia.bot.models.AnswerObject
import com.automafia.bot.models.Commands
import com.automafia.bot.models.ResponseObject
import com.automafia.bot.networking.Endpoints
import com.automafia.bot.networking.RequestManager
import java.net.http.HttpResponse

class CommonResponseObjectFunctions {
    private val manager: RequestManager = RequestManager()
    private val creatingScenarios: Creating = Creating()

    fun handleMethodResolver (userInput: String): ResponseObject {
        return when (userInput) {
            "/${Commands.START}" -> welcome(userInput)
            "/${Commands.CREATE}" -> creatingScenarios.create(userInput)
            "/${Commands.CONFIGLIST}" -> creatingScenarios.configList(userInput)
            "/${Commands.HEALTH}" -> health(userInput)
            "/${Commands.INITIALIZECONFIGS}" -> initializeConfigs(userInput)
            else -> unknown(userInput)
        }
    }

    fun welcome(userInput: String): ResponseObject {
        return ResponseObject(
            "You chose $userInput",
            "",
            listOf(
                AnswerObject("Создать игру", "/${Commands.CREATE}"),
                AnswerObject("Присоединиться", "/${Commands.CONNECT}"),
                AnswerObject("Оставить отзыв", "/${Commands.REVIEW}"),
                AnswerObject("health check", "/${Commands.HEALTH}")
            )
        )
    }

    fun unknown(userInput: String?): ResponseObject {
        return ResponseObject(
            "Неизвестная команда: $userInput",
            "Верниуться к началу",
            listOf(AnswerObject("Вернуться к началу", "/${Commands.START}"))
        )
    }

    fun health(userInput: String): ResponseObject {
        val response : HttpResponse<String> = manager.get(mapOf(), Endpoints.HEALTH)
        println(response.toString())
        return ResponseObject(
            "Выбрано: $userInput",
            "Сервис функционирует",
            listOf(
                AnswerObject("Вернуться к началу", "/${Commands.START}"),
                AnswerObject("Конфигурации", "/${Commands.INITIALIZECONFIGS}")
            )
        )
    }

    fun initializeConfigs(userInput: String): ResponseObject {
        val response: HttpResponse<String> = manager.get(mapOf(), Endpoints.INITIALIZE_CONFIGS)
        println(response.body())
        return ResponseObject(
            "Выбрано: $userInput",
            response.body(),
            listOf(
                AnswerObject("Вернуться к началу", "/${Commands.START}")
            )
        )
    }
}