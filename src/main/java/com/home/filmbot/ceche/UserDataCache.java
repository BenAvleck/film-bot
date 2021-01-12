package com.home.filmbot.ceche;


import com.home.filmbot.botapi.BotState;
import com.home.filmbot.botapi.handlers.fillingprofile.UserRequestData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private final Map<Integer, BotState> usersBotStates = new HashMap<>();
    private final Map<Integer, UserRequestData> usersRequestData = new HashMap<>();

    @Override
    public void setUserCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUserCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_MOVIE;
        }

        return botState;
    }
    @Override
    public void setUserRequestData(int userId, UserRequestData userRequestData){
        usersRequestData.put(userId, userRequestData);
    }

    @Override
    public UserRequestData getUserRequestData(int userId) {
        UserRequestData userRequestData = usersRequestData.get(userId);
        if(userRequestData == null){ userRequestData = new UserRequestData();}
    return userRequestData;
    }

}
