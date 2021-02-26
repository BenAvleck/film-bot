package com.home.filmbot.botapi.handlers.moviesearch;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmRequestData {
    String movieName;
    String url;
    String rate;
    String releaseDate;
    String country;
    String director;
    String tagline;
    String genre;
    String age;
    String duration;
    String actors;
    String description;
    String torrent;
    String imgUrl;
    String year;
    String EnglishTitle;

}
