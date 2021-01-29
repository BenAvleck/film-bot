package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.InputMessageHandler;

import com.home.filmbot.ceche.UserDataCache;
import com.home.filmbot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class FilmNameHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public FilmNameHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return null;

    }

    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_MOVIE;
    }


}
