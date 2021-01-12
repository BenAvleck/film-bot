package com.home.filmbot.ceche;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.fillingprofile.UserRequestData;


public interface DataCache {
    void setUserCurrentBotState(int userId, BotState botState);
    BotState getUserCurrentBotState(int userId);

    void setUserRequestData(int userId, UserRequestData userRequestData);
    UserRequestData getUserRequestData(int userId);
}
