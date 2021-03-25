package com.home.filmbot.botapi.handlers.moviesearch;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmRequestData {
    int Index;
    String movieName;
    String EnglishTitle;
    String rate;
    String nominations;
    String tagline;
    String releaseDate;
    String country;
    String director;
    String mainGenre;
    String genres;
    String age;
    String duration;
    String series;
    String actors;
    String description;
    String torrent;
    String imgUrl;
    String year;
    String url;

}
