package com.automafia.bot.models

data class ResponseObject(
    val responseText: String,
    val nextQuestion: String = "",
    val answerVariance: List<AnswerObject>
) {
    constructor(responseText: String, nexQuestion: String) : this(
        responseText,
        nexQuestion,
        listOf(AnswerObject("Вернуться в начало", "/${Commands.START}"))
    )
}
