package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.service.MenuService;
import com.home.filmbot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class SerialsHandler implements InputMessageHandler {
    private final ReplyMessagesService messagesService;
    private final MenuService menuService;

    public SerialsHandler(ReplyMessagesService messagesService, MenuService menuService) {
        this.messagesService = messagesService;
        this.menuService = menuService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (message.getText().equals("Сериалы")) {
            return menuService.getMenuMessage(message.getChatId(), null);
        }
        else {return processUsersInput(message);}
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_SERIALS;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.askDestiny");
        /*replyToUser.setReplyMarkup(getInlineMessageButtons());*/

        return replyToUser;
    }

}
