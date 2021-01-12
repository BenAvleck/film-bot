package com.home.filmbot.botapi.handlers.askmovie;

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
public class BotInfoHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public BotInfoHandler(UserDataCache userDataCache,
                          ReplyMessagesService messagesService) {
    this.messagesService = messagesService;
    this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.botInfo");
        userDataCache.setUserCurrentBotState(userId, BotState.ASK_MOVIE);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() { return BotState.BOT_INFO;}
}
