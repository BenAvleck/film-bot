package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.ceche.UserDataCache;
import com.home.filmbot.service.ReplyMessagesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MovieSearchHandler implements InputMessageHandler {
    @Value("${url.movie.search}")
    private String movieSearchTemplate;
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    @Override
    public SendMessage handle(Message message) {
        String messageText = message.getText();
        String url = processURL(messageText);
        SendMessage replyToUser;
        messagesService.getReplyText("reply.movieResponse");
        return null;
    }

    private String processURL(String filmName){return messagesService.getReplyText(movieSearchTemplate, filmName);}



    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_MOVIE;
    }
}
