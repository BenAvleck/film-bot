package com.home.filmbot.botapi.handlers.callbackquery;

import com.home.filmbot.service.FilmsDataService;
import com.home.filmbot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;



@Component
public class FilmInfoQueryHandler implements ICallbackQueryHandler{
    private final FilmsDataService filmsDataService;
    private final ReplyMessagesService messagesService;

    public FilmInfoQueryHandler(FilmsDataService filmsDataService, ReplyMessagesService messagesService) {
        this.filmsDataService = filmsDataService;
        this.messagesService = messagesService;
    }

    @Override
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        //Set variables
        long message_id = callbackQuery.getMessage().getMessageId();
        String call_data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        final int index; /*Integer.parseInt(callbackQuery.getData().split("\\|")[1]);*/
        String answer = messagesService.getReplyText("reply.movieResponse");

        EditMessageText callBackAnswer = new EditMessageText()
                .setChatId(chatId)
                .enableMarkdown(true)
                .setMessageId(Math.toIntExact(message_id))
                .enableWebPagePreview();


        if(call_data.split("\\|")[1].equals("next")){
            index = Integer.parseInt(callbackQuery.getData().split("\\|")[2])+5;
            answer += filmsDataService.getFilmList(index);
            callBackAnswer.setText( answer);
            callBackAnswer.setReplyMarkup(filmsDataService.getInlineMessageButtons(index));
        }
        else if(call_data.split("\\|")[1].equals("back")){
            index = Integer.parseInt(callbackQuery.getData().split("\\|")[2])-5;
            answer += filmsDataService.getFilmList(index);
            callBackAnswer.setText(answer);
            callBackAnswer.setReplyMarkup(filmsDataService.getInlineMessageButtons(index));
        }
        else {
            index = Integer.parseInt(callbackQuery.getData().split("\\|")[1]);
            return new SendMessage()
                    .setChatId(chatId)
                    .enableMarkdown(true)
                    .enableWebPagePreview()
                    .setText( filmsDataService.setFilmInfo(index));
        }

        return callBackAnswer;
    }


        private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.FILM_INFO;
    }
}
