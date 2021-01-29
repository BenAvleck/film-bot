package com.home.filmbot.appconfig;

import com.home.filmbot.botapi.FilmTelegramBot;
import com.home.filmbot.botapi.TelegramFacade;
import com.home.filmbot.botconfig.FilmTelegramBotConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
public class BotConfig {

    private FilmTelegramBotConfig botConfig;

    public BotConfig(FilmTelegramBotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public FilmTelegramBot filmTelegramBot(TelegramFacade telegramFacade) {

        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        FilmTelegramBot filmTelegramBot = new FilmTelegramBot(options, telegramFacade);
        filmTelegramBot.setBotPath(botConfig.getWebHookPath());
        filmTelegramBot.setBotToken(botConfig.getBotToken());
        filmTelegramBot.setBotUsername(botConfig.getUserName());

        return filmTelegramBot;
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}