package com.home.filmbot.service;

import com.home.filmbot.botapi.FilmTelegramBot;
import com.home.filmbot.botapi.handlers.moviesearch.FilmRequestData;
import com.home.filmbot.utils.Emojis;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Service
public class FilmsDataService {
    private final ReplyMessagesService messagesService;
    private List<FilmRequestData> filmList;
    FilmRequestData currentFilm;
    FilmTelegramBot telegramBot;

    public FilmsDataService(@Lazy FilmTelegramBot telegramBot, ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
        this.telegramBot = telegramBot;
    }

    public int size;

    @SneakyThrows
    public void setFilmList(String filmSearchTemplate) {
        Document doc  = Jsoup.connect(filmSearchTemplate)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();

        Elements films = doc.select("div.b-content__inline_item-link");
        size = films.size();
        if(size == 0) { return;}

        filmList = new ArrayList<>(films.size());
        for(Element element : films){
            FilmRequestData filmRequestData = new FilmRequestData();

            filmRequestData.setMovieName(element.select("a").text());
            filmRequestData.setYear(element.select("div").last().text().split(", ")[0]);
            filmRequestData.setCountry(element.select("div").last().text().split(", ")[1]);
            filmRequestData.setMainGenre(element.select("div").last().text().split(", ")[2]);
            filmRequestData.setUrl(element.select("a").first().absUrl("href"));

            filmList.add(filmRequestData);
        }
    }

    public String getFilmMaxInfo(int index){
        currentFilm = filmList.get(index);

        if (currentFilm.getActors()== null){setAllVariables();}

        String filmInfoMessage ="<b>" +Emojis.CLAPPER + currentFilm.getMovieName()+"</b>\n"
                +"<i>"+currentFilm.getEnglishTitle()+"</i>\n\n";

        if(currentFilm.getRate()!=null){filmInfoMessage+=currentFilm.getRate()+"\n\n";}
        if(currentFilm.getTagline()!=null){filmInfoMessage+=currentFilm.getTagline()+"\n\n";}
        if(currentFilm.getReleaseDate()!=null){filmInfoMessage+=Emojis.DATE+currentFilm.getReleaseDate()+"\n\n";}
        if(currentFilm.getDuration()!=null) {filmInfoMessage+=currentFilm.getDuration()+"\n\n";}
        if(currentFilm.getAge()!=null){filmInfoMessage+=currentFilm.getAge()+"\n\n";}
        if(currentFilm.getCountry()!=null){filmInfoMessage+="<b>Cтрана</b>"+Emojis.WHITE_FLAG+currentFilm.getCountry()+"\n\n";}
        if(currentFilm.getGenres()!=null){filmInfoMessage+=Emojis.FRAMES+ currentFilm.getGenres()+"\n\n";}
        if(currentFilm.getDirector()!=null){filmInfoMessage+=currentFilm.getDirector()+"\n\n";}
        if(currentFilm.getActors()!=null){filmInfoMessage+=currentFilm.getActors()+"\n\n";}
        if(currentFilm.getNominations()!=null){filmInfoMessage+=Emojis.TROPHY + currentFilm.getNominations()+"\n\n";}
        if(currentFilm.getSeries()!=null){filmInfoMessage+=Emojis.PUSHPIN+ currentFilm.getSeries()+"\n\n";}
        if(currentFilm.getDescription()!=null){filmInfoMessage+=Emojis.MEMO + currentFilm.getDescription();}
        filmInfoMessage+="<a href=\""+currentFilm.getImgUrl()+"\">&#8204;</a>";

        return filmInfoMessage;

    }

    @SneakyThrows
    private void setAllVariables() {

        Document doc  = Jsoup.connect(currentFilm.getUrl())
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        Elements filmInfo = doc.select("#main > div.b-container.b-wrapper > div > div.b-content__columns.pdt.clearfix > div.b-content__main");
        Elements postInfotableRight = filmInfo.select(" div.b-post__infotable.clearfix > div.b-post__infotable_right > div > table > tbody > tr");
        List<String> infoList = postInfotableRight.eachText();


        for(String info : infoList){
            String[] mark = info.split(" : ",2);
            switch (mark[0]) {
                case "Рейтинги" -> currentFilm.setRate(Emojis.STAR + "<b>" + mark[0] + "</b> : " + (mark[1].split("\\)", 2)[0]) + ")    " + (mark[1].split("\\)", 2)[1]));
                case "Входит в списки" -> currentFilm.setNominations("<b>" + mark[0] + "</b> : " + mark[1]);
                case "Слоган" -> currentFilm.setTagline(Emojis.PEN + "<b>" + mark[0] + "</b> : " + mark[1]);
                case "Дата выхода" -> currentFilm.setReleaseDate("<b>" + mark[0] + "</b> : " +mark[1]);
                case "Режиссер" -> currentFilm.setDirector(Emojis.ARTIST + "<b>" + mark[0] + "</b> : " + mark[1]);
                case "Жанр" -> currentFilm.setGenres("<b>" + mark[0] + "</b> : " + mark[1]);
                case "Возраст" -> currentFilm.setAge(Emojis.EYES + "<b>" + mark[0] + "</b> : " + mark[1]);
                case "Время" -> currentFilm.setDuration(Emojis.HOURGLASS + "<b>" + mark[0] + "</b> : " + mark[1]);
                case "Из серии" -> currentFilm.setSeries("<b>" + mark[0] + "</b> : " + mark[1]);
                case "В ролях актеры" -> currentFilm.setActors(Emojis.DANCER + "<b>" + mark[0] + "</b> : " + mark[1]);
            }
        }
        currentFilm.setDescription(filmInfo.select("div.b-post__description").eachText().get(0));
        currentFilm.setImgUrl(filmInfo.select("div.b-post__infotable.clearfix > div.b-post__infotable_left > div > a").attr("href"));
        currentFilm.setEnglishTitle(filmInfo.select("div.b-post__origtitle").text());
    }

    public String getFilmShortInfo(int index){
        currentFilm = filmList.get(index);

        if(currentFilm.getActors()==null){
            setAllVariables();
        }

        String filmInfoMessage = messagesService.getReplyText("reply.template.filmResponse"
                ,currentFilm.getUrl()
                ,currentFilm.getMovieName()
                ,currentFilm.getYear()
                ,currentFilm.getCountry()
                ,currentFilm.getMainGenre());
        filmInfoMessage+=""+currentFilm.getRate()+"\n\n";
        if(currentFilm.getTagline()!=null){filmInfoMessage+=currentFilm.getTagline()+"\n\n";}
        if(currentFilm.getDuration()!=null) {filmInfoMessage+=currentFilm.getDuration()+"\n\n";}
        if(currentFilm.getAge()!=null){filmInfoMessage+=currentFilm.getAge()+"\n\n";}
        filmInfoMessage+=currentFilm.getActors();
        filmInfoMessage+="<a href=\"jf+"+currentFilm.getImgUrl()+"\">&#8204;</a>";
        return filmInfoMessage;

    }

    public String getFilmList(int index){
        StringBuilder replyText = new StringBuilder();
        for(int i = index, n = 1; i < size && i < index + 5; n++, i++) {
            replyText.append(messagesService.getReplyText("reply.template.filmsRequest", n
                    , getURL(i)
                    , getMovieName(i)
                    , getYear(i)
                    , getCountry(i)
                    , getMainGenre(i)));
        }
        return replyText.toString();
    }

    public InlineKeyboardMarkup getInlineMessageButtonsForList(int index){
        int count = size-index;

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        int n = index;
        for (int i = 0; i < count && i<5 ; i++){
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(getEmoji(i).toString()).setCallbackData("FILMS|"+ n++));//Заменить handler не FILM_INFO, a film list
        }

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        if(index != 0) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Emojis.BACK.toString()).setCallbackData("FILMS|back|" + index)); //Заменить handler не FILM
        }
        if(count > 6) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Emojis.NEXT.toString()).setCallbackData("FILMS|next|"+index));
        }



        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
    private Emojis getEmoji(int index){
        return switch(index){
            case 0 -> Emojis.ONE;
            case 1 -> Emojis.TWO;
            case 2 -> Emojis.THREE;
            case 3 -> Emojis.FOUR;
            case 4 -> Emojis.FIVE;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }


    public String getMovieName(int index){
        return filmList.get(index).getMovieName();
    }
    public String getEnglishTitle(int index){
        return filmList.get(index).getEnglishTitle();
    }
    public String getURL(int index){
        return filmList.get(index).getUrl();
    }
    public String getRate(int index) {
        return filmList.get(index).getRate();
    }
    public String getNominations(int index) {
        return filmList.get(index).getNominations();
    }
    public String getReleaseDate(int index){
        return filmList.get(index).getReleaseDate();
    }
    public String getCountry(int index){
        return filmList.get(index).getCountry();
    }
    public String getDirector(int index){
        return filmList.get(index).getDirector();
    }
    public String getTagline(int index){ return filmList.get(index).getTagline(); }
    public String getMainGenre(int index){
        return filmList.get(index).getMainGenre();
    }
    public String getGenres(int index){
        return filmList.get(index).getGenres();
    }
    public String getAge(int index){ return filmList.get(index).getAge(); }
    public String getDuration(int index){return filmList.get(index).getDuration(); }
    public String getActors(int index){return filmList.get(index).getActors(); }
    public String getDescription(int index){return filmList.get(index).getDescription(); }
    public String getTorrents(int index){return filmList.get(index).getTorrent(); }
    public String getImgUrl(int index){ return filmList.get(index).getImgUrl(); }
    public String getYear(int index){ return filmList.get(index).getYear(); }
    public String getSeries(int index){ return filmList.get(index).getSeries(); }

    public ReplyMessagesService getMessagesService() {
        return this.messagesService;
    }

    public List<FilmRequestData> getFilmList() {
        return this.filmList;
    }
    public void setCurrentFilm(int index) {
        currentFilm = filmList.get(index);
        setAllVariables();
    }

    public FilmRequestData getCurrentFilm() {
        return this.currentFilm;
    }

    public FilmTelegramBot getTelegramBot() {
        return this.telegramBot;
    }

    public int getSize() {
        return this.size;
    }
}

