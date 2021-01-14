package com.home.filmbot.service.keyboards;

import com.home.filmbot.botapi.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BotInfoKeyboard implements IKeyboardMarkup{

    @Override
    public BotState getKeyboardName() {
        return BotState.BOT_INFO;
    }

    @Override
    public InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonStart = new InlineKeyboardButton().setText("Начать");
        InlineKeyboardButton buttonMenu = new InlineKeyboardButton().setText("Меню");

        buttonStart.setCallbackData("buttonStart");
        buttonMenu.setCallbackData("menu");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonStart);
        keyboardButtonsRow1.add(buttonMenu);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
