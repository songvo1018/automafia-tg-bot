package com.automafia.bot.networking

enum class Endpoints {
    GET_GAMES,
    GET_GAME,
    CREATE_GAME,
    CONNECT_GAME,
    SET_TARGET,
    NEXT_GO, //only for game owner
    NEXT_ROUND, //only for game owner
    END_GAME, //only for game owner
    HEALTH,
    CONFIG_LIST,
    CONFIG_SELECTED,
    INITIALIZE_CONFIGS,
}