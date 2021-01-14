package com.home.filmbot.botapi;

import com.home.filmbot.ceche.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyToUser = null;

        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from user: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(), update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getData());

            return  handleCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from user: {}, chatId: {}, with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyToUser = handleInputMessage(message);
        }

        return replyToUser;
    }

    private BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        int userId = callbackQuery.getFrom().getId();
        BotState botState = userDataCache.getUserCurrentBotState(userId);
        return botStateContext.processCallbackQuery(botState, callbackQuery);
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        botState = switch (inputMsg) {
            case "/start" -> BotState.BOT_INFO;
            case "Найти фильм", "Начать", "Хочу" -> BotState.FILLING_REQUEST;
            case "Помощь" -> BotState.SHOW_HELP_MENU;
            default -> userDataCache.getUserCurrentBotState(userId);
        };

        userDataCache.setUserCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
