package com.home.filmbot.botapi.handlers.menu;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.service.MainMenuService;
import com.home.filmbot.service.ReplyMessagesService;
import com.home.filmbot.utils.Emojis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class MainMenuHandler implements InputMessageHandler {
    ReplyMessagesService messagesService;
    MainMenuService menuService;

    public MainMenuHandler(ReplyMessagesService messagesService, MainMenuService menuService) {
        this.messagesService = messagesService;
        this.menuService = menuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return message.getText().equals("/start")? menuService.getMainMenuMessage(message.getChatId(),
                messagesService.getReplyText("reply.mainMenu.welcomeMessage",
                        Emojis.BLUSH,Emojis.OK_HAND,Emojis.HELP_MENU_WELCOME)):
                menuService.getMainMenuMessage(message.getChatId(),
                        messagesService.getEmojiReplyText("reply.helpMenu.welcomeMessage",
                                Emojis.HELP_MENU_WELCOME));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
