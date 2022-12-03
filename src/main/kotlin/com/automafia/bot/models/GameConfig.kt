package com.automafia.bot.models

import kotlinx.serialization.Serializable

@Serializable
data class GameConfig (val id: Int, val usersCount: Int, val doctorExist: Boolean, val maniacExist: Boolean, val mafiaUnanimousDecision: Boolean)