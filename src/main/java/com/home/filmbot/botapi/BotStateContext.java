package com.home.filmbot.botapi;


import com.home.filmbot.botapi.handlers.callback.CallbackQueryHandler;
import com.home.filmbot.botapi.handlers.message.InputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();
    private final Map<BotState, CallbackQueryHandler> callbackHandlers = new HashMap<>();


    public BotStateContext(List<InputMessageHandler> messageHandlers, List<CallbackQueryHandler> callbackHandlers) {
        callbackHandlers.forEach(handler -> this.callbackHandlers.put(handler.getHandlerName(), handler));
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public BotApiMethod<?> processCallbackQuery(BotState currentState, CallbackQuery callbackQuery){
        CallbackQueryHandler currentCallbackHandler = findCallbackHandler(currentState);
        return currentCallbackHandler.handle(callbackQuery);
    }

    public SendMessage processInputMessage(BotState currentState, Message message){
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private CallbackQueryHandler findCallbackHandler(BotState currentState) {
        if (isFillingRequestState(currentState)){
            return callbackHandlers.get(BotState.FILLING_REQUEST);
        }
        return callbackHandlers.get(currentState);
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
