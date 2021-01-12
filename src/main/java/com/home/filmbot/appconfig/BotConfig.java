package com.home.filmbot.appconfig;

import com.home.filmbot.FilmTelegramBot;
import com.home.filmbot.botapi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public FilmTelegramBot filmTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        FilmTelegramBot filmTelegramBot = new FilmTelegramBot(options, telegramFacade);
        filmTelegramBot.setWebHookPath(webHookPath);
        filmTelegramBot.setBotToken(botToken);
        filmTelegramBot.setBotUserName(botUserName);

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