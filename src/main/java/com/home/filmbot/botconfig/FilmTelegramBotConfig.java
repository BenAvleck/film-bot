package com.home.filmbot.botconfig;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "telegrambot")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class FilmTelegramBotConfig {
    String webHookPath;
    String userName;
    String botToken;
}
