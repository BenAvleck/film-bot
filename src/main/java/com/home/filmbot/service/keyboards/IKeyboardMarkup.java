package com.home.filmbot.service.keyboards;

import com.home.filmbot.botapi.BotState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface IKeyboardMarkup {
    BotState getKeyboardName();
    InlineKeyboardMarkup getInlineMessageButtons();
}
