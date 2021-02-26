package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.service.FilmsDataService;
import com.home.filmbot.service.MenuService;
import com.home.filmbot.service.ReplyMessagesService;
import com.home.filmbot.utils.Emojis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class FilmsHandler implements InputMessageHandler {
    private final ReplyMessagesService messagesService;
    private final MenuService menuService;
    private final FilmsDataService filmService;

    public FilmsHandler(ReplyMessagesService messagesService, MenuService menuService, FilmsDataService filmService) {
        this.messagesService = messagesService;
        this.menuService = menuService;
        this.filmService = filmService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (message.getText().equals("Фильмы")) {
            return menuService.getMenuMessage(message.getChatId(), null);
        }
        else {return processUsersInput(message);}
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_FILMS;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = switch (usersAnswer){
            case "Лучшие" -> processBests();
            case "Жанры" -> processGenres();
            case "Новинки" -> processNews();
            default -> messagesService.getReplyMessage(chatId, "reply.keyboard.fail", Emojis.NOTIFICATION_MARK_FAILED, Emojis.FAIL, Emojis.NOTIFICATION_INFO_MARK);
        };
        return replyToUser;
    }

    private SendMessage processNews() {
        filmService.setFilmList(messagesService.getReplyText("url.films.new"));

        return null;
    }

    private SendMessage processGenres() {
    return null;
    }

    private SendMessage processBests() {
        return null;
    }

}
