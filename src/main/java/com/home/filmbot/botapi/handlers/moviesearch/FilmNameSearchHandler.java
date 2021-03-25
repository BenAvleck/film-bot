package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.FilmTelegramBot;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.service.FilmsDataService;
import com.home.filmbot.service.ReplyMessagesService;
import com.home.filmbot.utils.Emojis;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/*Обработчик запросов фильма по названию*/

@Slf4j
@Component
public class FilmNameSearchHandler implements InputMessageHandler {
    private String filmSearchTemplate;
    private final ReplyMessagesService messagesService;
    private final FilmTelegramBot telegramBot;
    private final FilmsDataService filmsDataService;
    private final DeleteMessage deleteMessage = new DeleteMessage();

    public FilmNameSearchHandler(FilmsDataService filmsDataService, ReplyMessagesService messagesService, @Lazy FilmTelegramBot telegramBot) {
        this.messagesService = messagesService;
        this.telegramBot = telegramBot;
        this.filmsDataService = filmsDataService;

    }
    @SneakyThrows
    @Override
    public SendMessage handle(Message message) {
        long chatId = message.getChatId();
        String messageText = message.getText();
        filmSearchTemplate = messagesService.getReplyText("url.search.filter", message.getText());
        SendMessage replyMessage;
        String replyText;
//        telegramBot.sendMessage(messagesService.getReplyMessage(chatId, "reply.movieResponse"));


        filmsDataService.setFilmList(filmSearchTemplate);

        if (filmsDataService.getSize()==0)
            return messagesService.getReplyMessage(chatId,"reply.notFound", Emojis.NOTIFICATION_MARK_FAILED, messageText);

        replyText = messagesService.getReplyText("reply.movieResponse.name", messageText);
        replyMessage = new SendMessage()
                .enableHtml(true)
                .setChatId(chatId);

        replyText+= filmsDataService.getFilmList(0);
        replyMessage.setText(replyText);
        replyMessage.setReplyMarkup(filmsDataService.getInlineMessageButtonsForList(0));

        return replyMessage;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_MOVIE;
    }
}

