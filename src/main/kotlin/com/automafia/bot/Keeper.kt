package com.automafia.bot

import com.automafia.bot.handlers.CallbackHandler
import com.automafia.bot.handlers.MessageHandler
import com.automafia.bot.models.ResponseObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
class Keeper: TelegramLongPollingBot() {

    @Value("\${telegram.botName}")
    private val botName : String = ""

    @Value("\${telegram.token}")
    private val token : String = ""

    override fun getBotToken(): String = token

    override fun getBotUsername(): String = botName

    private val messageHandler: MessageHandler = MessageHandler()
    private val callbackHandler: CallbackHandler = CallbackHandler()

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            sendNotification(update.message.chatId.toString(), messageHandler.handle(update))
        } else if (update.hasCallbackQuery()) {
            sendNotification(update.callbackQuery.from.id.toString(), callbackHandler.handle(update))
        }
    }

    private fun sendNotification(chatId: String, responseObject: ResponseObject) {
        val keyboard = Keyboard()
        var finalMessage = responseObject.responseText + " \n" + responseObject.nextQuestion
        val responseMessage = SendMessage(chatId, finalMessage)
        responseMessage.enableMarkdownV2(true)
        if (responseObject.answerVariance.isNotEmpty()) {
            val buttons = responseObject.answerVariance.map { answer ->
                val button = InlineKeyboardButton()
                button.text = answer.text
                button.callbackData = answer.callback
                button
            }
            responseMessage.replyMarkup = keyboard.getInlineKeyBoardMessage(
                listOf(buttons)
            )
        }
        execute(responseMessage)
    }
}