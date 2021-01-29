package com.home.filmbot.botapi;

import com.home.filmbot.botapi.handlers.callbackquery.CallbackQueryFacade;
import com.home.filmbot.ceche.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private CallbackQueryFacade callbackQueryFacade;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, CallbackQueryFacade callbackQueryFacade) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.callbackQueryFacade = callbackQueryFacade;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            log.info("New callbackQuery from User: {} with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    update.getCallbackQuery().getData());
            return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }



    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        botState = switch (inputMsg) {
            case "/start", "/help" -> BotState.SHOW_MAIN_MENU;
            case "Фильмы" -> BotState.SEARCH_FILMS;
            case "Сериалы" -> BotState.SEARCH_SERIALS;
            case "Мультфильмы" -> BotState.SEARCH_CARTOONS;
            case "Аниме" -> BotState.SEARCH_ANIME;
            case "Избранное" -> BotState.FAVORITES;
            default -> userDataCache.getUserCurrentBotState(userId);
        };

        userDataCache.setUserCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
