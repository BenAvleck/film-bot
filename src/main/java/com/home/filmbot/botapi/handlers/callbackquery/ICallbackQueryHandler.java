package com.home.filmbot.botapi.handlers.callbackquery;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface ICallbackQueryHandler {
    BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery);

    CallbackQueryType getHandlerQueryType();
}
