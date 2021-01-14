package com.home.filmbot.botapi.handlers.message;


import com.home.filmbot.botapi.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**Обработчик сообщений
 */

public interface InputMessageHandler {
    SendMessage handle(Message message);
    BotState getHandlerName();
}
