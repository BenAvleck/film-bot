package com.home.filmbot.service;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.service.keyboards.IKeyboardMarkup;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyboardMarkupService {
    private final Map<BotState, IKeyboardMarkup> keyboards = new HashMap<>();

    public KeyboardMarkupService(List<IKeyboardMarkup> keyboards) {
        keyboards.forEach(keyboard -> this.keyboards.put(keyboard.getKeyboardName(), keyboard));
    }

    public InlineKeyboardMarkup getInlineMessageButtons(BotState currentState){
        IKeyboardMarkup keyboardMarkup = findKeyboardMarkup(currentState);

        return keyboardMarkup.getInlineMessageButtons();
    }

    private IKeyboardMarkup findKeyboardMarkup(BotState currentState) {
        return keyboards.get(currentState);
    }
}
