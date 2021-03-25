package com.home.filmbot.botapi.handlers.callbackquery;

import com.home.filmbot.botapi.FilmTelegramBot;
import com.home.filmbot.service.FilmsDataService;
import com.home.filmbot.service.ReplyMessagesService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaxInfoQueryHandler implements ICallbackQueryHandler{
    private final FilmsDataService filmsDataService;
    private final ReplyMessagesService messagesService;
    private final FilmTelegramBot telegramBot;

    public MaxInfoQueryHandler(FilmsDataService filmsDataService, ReplyMessagesService messagesService,@Lazy FilmTelegramBot telegramBot) {
        this.filmsDataService = filmsDataService;
        this.messagesService = messagesService;
        this.telegramBot = telegramBot;
    }

    @Override
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        //Set variables
        String callbackId = callbackQuery.getId();
        long message_id = callbackQuery.getMessage().getMessageId();
        String call_data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        final int index = Integer.parseInt(callbackQuery.getData().split("\\|")[2]);;

        EditMessageText callBackAnswer = new EditMessageText()
                .setChatId(chatId)
                .enableHtml(true)
                .setMessageId(Math.toIntExact(message_id))
                /*.enableWebPagePreview()*/;

        if(call_data.split("\\|")[1].equals("detail")){
            callBackAnswer.setText(filmsDataService.getFilmMaxInfo(index))
                    .setReplyMarkup(getInlineMessageButtonsForMaxFilm(index, "detail"));
            telegramBot.sendAnswerCallbackQuery(sendAnswerCallbackQuery("Подробнее",false, callbackId));
        }
        else
        {
            callBackAnswer.setText(filmsDataService.getFilmShortInfo(index))
                    .setReplyMarkup(getInlineMessageButtonsForMaxFilm(index, "briefly"));
            telegramBot.sendAnswerCallbackQuery(sendAnswerCallbackQuery("Коротко",false, callbackId));
        }

        return callBackAnswer;
    }
    public InlineKeyboardMarkup getInlineMessageButtonsForMaxFilm(int index, String button){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        if(button.equals("detail")) {
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Коротко").setCallbackData("INFO|briefly|" + index));
        }
        else{
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Подробнее").setCallbackData("INFO|detail|" + index));
        }
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Добавить в избранное").setCallbackData("SUBSCRIBE|" + index));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Смотреть онлайн").setCallbackData("WATCH|" + index));



        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, String callbackQueryId) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQueryId);
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.INFO;
    }
}
