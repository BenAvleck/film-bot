package com.home.filmbot.botapi.handlers.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class UnsubscribeMovieQueryHandlerI implements ICallbackQueryHandler {
    @Override
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return null;
    }
}
