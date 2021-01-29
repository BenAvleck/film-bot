package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.ceche.UserDataCache;
import com.home.filmbot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MovieSearchHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    @Override
    public SendMessage handle(Message message) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return null;
    }
}
