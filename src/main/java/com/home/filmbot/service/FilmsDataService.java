package com.home.filmbot.service;

import com.home.filmbot.botapi.FilmTelegramBot;
import com.home.filmbot.botapi.handlers.moviesearch.FilmRequestData;
import com.home.filmbot.utils.Emojis;
import lombok.Getter;
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


@Getter
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
            filmRequestData.setGenre(element.select("div").last().text().split(", ")[2]);
            filmRequestData.setUrl(element.select("a").first().absUrl("href"));

            filmList.add(filmRequestData);
        }
    }

    public void setCurrentFilm(int index){
        if(index <= size-1){
            currentFilm = filmList.get(index);
        }
    }

    public String getFullCurrentFilmImfo(){
        String filmInfoMessage = "";
        filmInfoMessage += messagesService.getReplyText("reply.template.filmResponse"
                ,currentFilm.getMovieName()
                ,currentFilm.getUrl()
                ,currentFilm.getYear()
                ,currentFilm.getCountry()
                ,currentFilm.getGenre());
        filmInfoMessage+=""+currentFilm.getRate()+"\n\n";
        if(currentFilm.getTagline()!=null){filmInfoMessage+=currentFilm.getTagline()+"\n\n";}
        filmInfoMessage+=currentFilm.getDuration()+"\n\n";
        if(currentFilm.getAge()!=null){filmInfoMessage+=currentFilm.getAge()+"\n\n";}
        filmInfoMessage+=currentFilm.getActors();
        filmInfoMessage+=
        filmInfoMessage+="[.]("+currentFilm.getImgUrl()+")";

        return filmInfoMessage;
    }

    @SneakyThrows
    public String setFilmInfo(int index){
        currentFilm = filmList.get(index);
        String filmInfoMessage = "";

        Document doc  = Jsoup.connect(currentFilm.getUrl())
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        Elements filmInfo = doc.select("#main > div.b-container.b-wrapper > div > div.b-content__columns.pdt.clearfix > div.b-content__main");
        Elements postInfotableRight = filmInfo.select(" div.b-post__infotable.clearfix > div.b-post__infotable_right > div > table > tbody > tr");
        Elements postInfotableLeft = filmInfo.select("div.b-post__infotable.clearfix > div.b-post__infotable_left > div > a");
        Elements postDescriptintTitle = filmInfo.select("div.b-post__description");
        List<String> infoList = postInfotableRight.eachText();



        for(String info : infoList){
            String[] mark = info.split(" : ",2);
            if(mark[0].equals("Рейтинги"))
                currentFilm.setRate(Emojis.STAR+"*"+mark[0]+"* : "+ (mark[1].split("\\)", 2)[0])+")    "+(mark[1].split("\\)", 2)[1]) );
            else if (mark[0].equals("Слоган"))
                currentFilm.setTagline(Emojis.PEN+"*"+mark[0]+"* : "+mark[1]);
            else if (mark[0].equals("Дата выхода"))
                currentFilm.setReleaseDate(mark[1]);
            else if (mark[0].equals("Режиссер"))
                currentFilm.setDirector(Emojis.ARTIST+"*"+mark[0]+"* : "+mark[1]);
            else if (mark[0].equals("Возраст"))
                currentFilm.setAge(Emojis.EYES+"*"+mark[0]+"* : "+mark[1]);
            else if (mark[0].equals("Время"))
                currentFilm.setDuration(Emojis.HOURGLASS+"*"+mark[0]+"* : "+mark[1]);
            else if (mark[0].equals("В ролях актеры"))
                currentFilm.setActors(Emojis.DANCER+"*"+mark[0]+"* : "+mark[1]);
        }
        currentFilm.setDescription(postDescriptintTitle.eachText().get(0));
        currentFilm.setImgUrl(postInfotableLeft.attr("href"));
        currentFilm.setEnglishTitle(filmInfo.select("div.b-post__origtitle").text());

        filmInfoMessage += messagesService.getReplyText("reply.template.filmResponse"
                ,currentFilm.getMovieName()
                ,currentFilm.getUrl()
                ,currentFilm.getYear()
                ,currentFilm.getCountry()
                ,currentFilm.getGenre());
        filmInfoMessage+=""+currentFilm.getRate()+"\n\n";
        if(currentFilm.getTagline()!=null){filmInfoMessage+=currentFilm.getTagline()+"\n\n";}
        filmInfoMessage+=currentFilm.getDuration()+"\n\n";
        if(currentFilm.getAge()!=null){filmInfoMessage+=currentFilm.getAge()+"\n\n";}
        filmInfoMessage+=currentFilm.getActors();
        filmInfoMessage+="[.]("+currentFilm.getImgUrl()+")";

        return filmInfoMessage;
    }

    public String getFilmList(int index){
        String replyText = "";
        for(int i = index, n = 1; i < size && i < index + 5; n++, i++) {
            replyText += messagesService.getReplyText("reply.template.filmsRequest", n
                    ,getMovieName(i)
                    ,getURL(i)
                    ,getYear(i)
                    ,getCountry(i)
                    ,getGenre(i));
        }
        return replyText;
    }

    public InlineKeyboardMarkup getInlineMessageButtons(int index){
        int count = size-index;

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        int n = index;
        for (int i = 0; i < count && i<5 ; i++){
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(getEmoji(i).toString()).setCallbackData("FILM_INFO|"+ n++));
        }

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        if(index != 0) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Emojis.BACK.toString()).setCallbackData("FILM_INFO|back|" + index));
        }
        if(count > 6) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Emojis.NEXT.toString()).setCallbackData("FILM_INFO|next|"+index));
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
    public String getGenre(int index){
        return filmList.get(index).getGenre();
    }
    public String getAge(int index){
        return filmList.get(index).getAge();
    }
    public String getDuration(int index){
        return filmList.get(index).getDuration();
    }
    public String getActors(int index){
        return filmList.get(index).getActors();
    }
    public String getDescription(int index){
        return filmList.get(index).getDescription();
    }
    public String getTorrents(int index){
        return filmList.get(index).getTorrent();
    }
    public String getImgUrl(int index){ return filmList.get(index).getImgUrl(); }
    public String getYear(int index){ return filmList.get(index).getYear(); }

}

