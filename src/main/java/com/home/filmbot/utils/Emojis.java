package com.home.filmbot.utils;


import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

/**
 * Смайлики
 **/
@AllArgsConstructor
public enum Emojis {
    OK_HAND(EmojiParser.parseToUnicode(":ok_hand:")),
    TIME_DEPART(EmojiParser.parseToUnicode(":clock8:")),
    TIME_ARRIVAL(EmojiParser.parseToUnicode(":clock3:")),
    SUCCESS_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    NOTIFICATION_MARK_FAILED(EmojiParser.parseToUnicode(":exclamation:")),
    SUCCESS_UNSUBSCRIBED(EmojiParser.parseToUnicode(":negative_squared_cross_mark:")),
    SUCCESS_SUBSCRIBED(EmojiParser.parseToUnicode(":mailbox:")),
    FAIL(EmojiParser.parseToUnicode(":x:")),
    NOTIFICATION_INFO_MARK(EmojiParser.parseToUnicode(":information_source:")),
    NOTIFICATION_PRICE_UP(EmojiParser.parseToUnicode(":chart_with_upwards_trend:")),
    NOTIFICATION_PRICE_DOWN(EmojiParser.parseToUnicode(":chart_with_downwards_trend:")),
    HELP_MENU_WELCOME(EmojiParser.parseToUnicode(":hatched_chick:")),
    BLUSH(EmojiParser.parseToUnicode(":blush:")),
    STAR(EmojiParser.parseToUnicode(":star:")),
    ARTIST(EmojiParser.parseToUnicode(":man_artist:")),
    HOURGLASS(EmojiParser.parseToUnicode(":hourglass_flowing_sand:")),
    DANCER(EmojiParser.parseToUnicode(":dancer:")),
    PEN(EmojiParser.parseToUnicode(":lower_left_fountain_pen:")),
    EYES(EmojiParser.parseToUnicode(":eyes:")),
    ONE(EmojiParser.parseToUnicode(":one:")),
    TWO(EmojiParser.parseToUnicode(":two:")),
    THREE(EmojiParser.parseToUnicode(":three:")),
    FOUR(EmojiParser.parseToUnicode(":four:")),
    FIVE(EmojiParser.parseToUnicode(":five:")),
    POINT_DOWN(EmojiParser.parseToUnicode(":point_down:")),
    BACK(EmojiParser.parseToUnicode(":arrow_left:")),
    NEXT(EmojiParser.parseToUnicode(":arrow_right:")),
    CLAPPER(EmojiParser.parseToUnicode(":clapper:")),
    DATE(EmojiParser.parseToUnicode(":date:")),
    WHITE_FLAG(EmojiParser.parseToUnicode(":waving_white_flag:")),
    FRAMES(EmojiParser.parseToUnicode(":film_frames")),
    TROPHY(EmojiParser.parseToUnicode(":trophy")),
    PUSHPIN(EmojiParser.parseToUnicode(":round_pushpin")),
    MEMO(EmojiParser.parseToUnicode(":memo:")),
    RELAXED(EmojiParser.parseToUnicode(":relaxed:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
