package com.home.filmbot.botapi;

import com.home.filmbot.botapi.handlers.callbackquery.CallbackQueryFacade;
import com.home.filmbot.ceche.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Slf4j
@Component
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private CallbackQueryFacade callbackQueryFacade;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, CallbackQueryFacade callbackQueryFacade) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.callbackQueryFacade = callbackQueryFacade;
    }

    public BotApiMethod<? extends Serializable> handleUpdate(Update update) {
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
        BotState botState = null;
        SendMessage replyMessage;


            botState = switch (inputMsg) {
                case "/help", "/start", "Назад" -> BotState.SHOW_MAIN_MENU;
                case "Фильмы", "Назад к фильмам" -> BotState.SEARCH_FILMS;
                case "Сериалы", "Назад к сериалам" -> BotState.SEARCH_SERIALS;
                case "Мультфильмы", "Назад к мультфильмам" -> BotState.SEARCH_CARTOONS;
                case "Аниме", "Назад к аниме" -> BotState.SEARCH_ANIME;
                case "Избранное" -> BotState.FAVORITES;
                case "Лучшие", "Жанры", "Новинки" -> userDataCache.getUserCurrentBotState(userId);
                default -> BotState.SEARCH_MOVIE; /*userDataCache.getUserCurrentBotState(userId);*/
            };
            userDataCache.setUserCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
