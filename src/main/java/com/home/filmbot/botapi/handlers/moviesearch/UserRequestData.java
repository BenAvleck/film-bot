package com.home.filmbot.botapi.handlers.moviesearch;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestData {
    String movieName;

}
