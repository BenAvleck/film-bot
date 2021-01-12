package com.home.filmbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ReplyMessagesService {
private final LocaleMessageService localeMessageService;

    public ReplyMessagesService(LocaleMessageService messageService) {
        this.localeMessageService = messageService;
    }
    public SendMessage getReplyMessage(long chatId, String replyMessage){return new SendMessage(chatId, localeMessageService.getMessages(replyMessage));}
    public SendMessage getReplyMessage(long chatId, String replyMessage, Object... args){return new SendMessage(chatId, localeMessageService.getMessages(replyMessage,args));}

}
