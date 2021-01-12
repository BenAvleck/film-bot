package com.home.filmbot.botapi;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
    messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message){
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isFillingRequestState(currentState)){
            return messageHandlers.get(BotState.FILLING_REQUEST);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isFillingRequestState(BotState currentState) {
        return switch (currentState) {
            case ASK_MOVIE, MOVIE_REPLY, FILLING_REQUEST -> true;
            default -> false;
        };
    }
}
