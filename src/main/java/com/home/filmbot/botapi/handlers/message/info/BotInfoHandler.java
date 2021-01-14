package com.home.filmbot.botapi.handlers.message.info;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.message.InputMessageHandler;
import com.home.filmbot.service.KeyboardMarkupService;
import com.home.filmbot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Slf4j
@Component
public class BotInfoHandler implements InputMessageHandler {
    private final ReplyMessagesService messagesService;
    private final KeyboardMarkupService markupService;

    public BotInfoHandler(KeyboardMarkupService markupService, ReplyMessagesService messagesService) {
    this.messagesService = messagesService;
    this.markupService = markupService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.botInfo");
        replyToUser.setReplyMarkup(markupService.getInlineMessageButtons(getHandlerName()));

        return replyToUser;
    }

    @Override
    public BotState getHandlerName() { return BotState.BOT_INFO;}
}
