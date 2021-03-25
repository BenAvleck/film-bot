package com.home.filmbot.cache;

import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.moviesearch.FilmRequestData;


public interface DataCache {
    void setUserCurrentBotState(int userId, BotState botState);
    BotState getUserCurrentBotState(int userId);

    void setUserRequestData(int userId, FilmRequestData filmRequestData);
    FilmRequestData getUserRequestData(int userId);
}
