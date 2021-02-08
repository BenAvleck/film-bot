package com.home.filmbot.botapi.handlers.moviesearch;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.FilmTelegramBot;
import com.home.filmbot.botapi.handlers.InputMessageHandler;
import com.home.filmbot.service.ReplyMessagesService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/*Обработчик запросов фильма по названию*/
@Component
public class FilmNameSearchHandler implements InputMessageHandler {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FilmNameSearchHandler.class);
    private String filmSearchTemplate;
    private final ReplyMessagesService messagesService;
    private final FilmTelegramBot telegramBot;

    public FilmNameSearchHandler( ReplyMessagesService messagesService, @Lazy FilmTelegramBot telegramBot) {
        this.messagesService = messagesService;
        this.telegramBot = telegramBot;
    }
    @SneakyThrows
    @Override
    public SendMessage handle(Message message) {
        filmSearchTemplate = messagesService.getReplyText("url.search.filter", message.getText());
        long chatId = message.getChatId();

        telegramBot.sendMessage(messagesService.getReplyMessage(chatId, "reply.movieResponse"));


            Document doc  = Jsoup.connect(filmSearchTemplate)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();

            Elements listName = doc.select("div.b-content__inline_item-link");
            for(Element element : listName){
                System.out.println(element.select("a").text() + " ["+element.select("div").text() + "] \n"+ element.select("a").first().absUrl("href"));

                telegramBot.sendMessage(chatId,(element.select("a").text() + " ["+element.select("div").last().text() + "] \n"+ element.select("a").first().absUrl("href")));
            }

        return null;



        /*Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
         log.info(doc.title());

        Elements newsHeadlines = doc.select("#mp-itn b a");

        for (Element headline : newsHeadlines) {
            log.info("%s\n\t%s",
                    headline.attr("title"), headline.absUrl("href"));
        }*/

    }


    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_MOVIE;
    }
}
