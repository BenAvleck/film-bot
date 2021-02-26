package com.home.filmbot.ceche;


import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.moviesearch.FilmRequestData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private final Map<Integer, BotState> usersBotStates = new HashMap<>();
    private final Map<Integer, FilmRequestData> usersRequestData = new HashMap<>();

    @Override
    public void setUserCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUserCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.SEARCH_MOVIE;
        }

        return botState;
    }
    @Override
    public void setUserRequestData(int userId, FilmRequestData filmRequestData){
        usersRequestData.put(userId, filmRequestData);
    }

    @Override
    public FilmRequestData getUserRequestData(int userId) {
        FilmRequestData filmRequestData = usersRequestData.get(userId);
        if(filmRequestData == null){ filmRequestData = new FilmRequestData();}
    return filmRequestData;
    }

}
