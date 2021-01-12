package com.home.filmbot.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Locale;

@Service
public class LocaleMessageService {
    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }
    public String getMessages(String message){return messageSource.getMessage(message, null, locale);}
    public String getMessages(String message, Object... args){return messageSource.getMessage(message, args,locale);}
}
