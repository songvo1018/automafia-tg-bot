package com.automafia.bot

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

class Keyboard {
    fun getInlineKeyBoardMessage(allButtons: List<List<InlineKeyboardButton>>): InlineKeyboardMarkup {
        val inlineKeyboardMarkup = InlineKeyboardMarkup()
        inlineKeyboardMarkup.keyboard = allButtons.map { rowButtons ->
            val row: MutableList<InlineKeyboardButton> = ArrayList()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        return inlineKeyboardMarkup
    }

    fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboard? {
        val markup = ReplyKeyboardMarkup()
        markup.keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        return markup
    }
}