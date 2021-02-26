package com.home.filmbot.botapi.handlers.callbackquery;

import com.home.filmbot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

@Component
public class CallbackQueryFacade {
    private ReplyMessagesService messagesService;
    private List<ICallbackQueryHandler> ICallbackQueryHandlers;

    public CallbackQueryFacade(ReplyMessagesService messagesService,
                               List<ICallbackQueryHandler> ICallbackQueryHandlers) {
        this.messagesService = messagesService;
        this.ICallbackQueryHandlers = ICallbackQueryHandlers;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery usersQuery) {
        CallbackQueryType usersQueryType = CallbackQueryType.valueOf(usersQuery.getData().split("\\|")[0]);

        Optional<ICallbackQueryHandler> queryHandler = ICallbackQueryHandlers.stream().
                filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType)).findFirst();

        return queryHandler.get().handleCallbackQuery(usersQuery);/*queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery)).
                orElse(messagesService.getWarningReplyMessage(usersQuery.getMessage().getChatId(), "reply.query.failed"));
*/    }
}
