package com.home.filmbot.botapi.handlers.callback.info;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.callback.CallbackQueryHandler;
import com.home.filmbot.ceche.UserDataCache;
import com.home.filmbot.service.MainMenuService;
import com.home.filmbot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Slf4j
@Component
public class BotInfoCallbackHandler extends CallbackQueryHandler {
    private final ReplyMessagesService messagesService;
    private final UserDataCache userDataCache;
    private final MainMenuService mainMenuService;

    public BotInfoCallbackHandler(ReplyMessagesService messagesService, UserDataCache userDataCache, MainMenuService mainMenuService) {
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callbackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");

        switch (buttonQuery.getData()) {
            case "buttonStart" -> {
                callbackAnswer = messagesService.getReplyMessage(chatId, "reply.askMovie");
                userDataCache.setUserCurrentBotState(userId, BotState.MOVIE_REPLY);
            }
            case "menu" -> mainMenuService.getMainMenuMessage(chatId, "Главное меню");
        }

        return callbackAnswer;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.BOT_INFO;
    }
}
