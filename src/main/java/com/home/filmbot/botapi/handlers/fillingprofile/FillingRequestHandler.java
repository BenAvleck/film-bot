package com.home.filmbot.botapi.handlers.fillingprofile;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.InputMessageHandler;
import com.home.filmbot.ceche.UserDataCache;
import com.home.filmbot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class FillingRequestHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public FillingRequestHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_REQUEST)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.ASK_MOVIE);
        }
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        UserRequestData requestData = userDataCache.getUserRequestData(userId);
        BotState botState = userDataCache.getUserCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_MOVIE)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askMovie");
            userDataCache.setUserCurrentBotState(userId, BotState.MOVIE_REPLY);
        }
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_REQUEST;
    }
}
