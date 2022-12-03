package com.automafia.bot.handlers.scenarios

import com.automafia.bot.models.AnswerObject
import com.automafia.bot.models.Commands
import com.automafia.bot.models.GameConfig
import com.automafia.bot.models.ResponseObject
import com.automafia.bot.networking.Endpoints
import com.automafia.bot.networking.RequestManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.http.HttpResponse

class Creating {
    private val manager: RequestManager = RequestManager()

    fun create(userInput: String): ResponseObject {
        return ResponseObject(
            "Выбрано: $userInput",
            "Конфигурация игры",
            listOf(
                AnswerObject("Вернуться к началу", "/${Commands.START}"),
                AnswerObject("Создать свою", "/${Commands.SETTINGS}"),
                AnswerObject("Выбрать из существующих", "/${Commands.CONFIGLIST}")
            )
        )
    }

    fun configList(userInput: String): ResponseObject {
        val response : HttpResponse<String> = manager.get(mapOf(), Endpoints.CONFIG_LIST)
        val gameConfigs: List<GameConfig> = Json.decodeFromString(response.body())
        var configString = "Доступны следующие конфигураци\\:"
        val buttonsToChose: MutableList<AnswerObject> = mutableListOf()
        for (game in gameConfigs) {
            configString += """
            ${game.id}\. Кол\-во игроков \: ${game.usersCount}
            \- Маньяк\: ${game.maniacExist}
            \- Доктор\: ${game.doctorExist}
            \- Единомышленное решение 
            мафии\: ${game.mafiaUnanimousDecision}
            \- \- \- \- \- \- \- \- \- \- \- 
            """.trimIndent()
            buttonsToChose.add(AnswerObject(game.id.toString(), "/${Commands.SELECT}${game.id}"))
        }
        return ResponseObject(
            "Выбрано: $userInput",
            configString,
            buttonsToChose
        )
    }

    fun selectedConfig(userInput: String, userName: String): ResponseObject {
        val parsedSelectedConfigId = userInput.substringAfter(Commands.SELECT.toString())
        val response : HttpResponse<String> = manager.post(
            values=mapOf(),
            command=Endpoints.CONFIG_SELECTED,
            params=mapOf(Pair("creator", userName), Pair("gameConfigId", parsedSelectedConfigId))
        )
        val gameKey: Int = Json.decodeFromString(response.body())

        return ResponseObject(
            "Вы создали игру\\. Назовите номер другим игрокам: $gameKey",
            "Она начнется\\, когда все игроки присоединятся",
            listOf()
        )
//        TODO: как инициировать сообщение от бота пользователю?
    }
}