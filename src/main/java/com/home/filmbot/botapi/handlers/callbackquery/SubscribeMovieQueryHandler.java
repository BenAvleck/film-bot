package com.home.filmbot.botapi.handlers.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/*
Обраабтывает запрос "Добавить в избранное".
* */

@Component
public class SubscribeMovieQueryHandler implements CallbackQueryHandler{
    @Override
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return null;
    }
}
