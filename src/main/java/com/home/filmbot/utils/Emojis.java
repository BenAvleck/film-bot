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
    NOTIFICATION_BELL(EmojiParser.parseToUnicode(":bell:")),
    NOTIFICATION_INFO_MARK(EmojiParser.parseToUnicode(":information_source:")),
    NOTIFICATION_PRICE_UP(EmojiParser.parseToUnicode(":chart_with_upwards_trend:")),
    NOTIFICATION_PRICE_DOWN(EmojiParser.parseToUnicode(":chart_with_downwards_trend:")),
    HELP_MENU_WELCOME(EmojiParser.parseToUnicode(":hatched_chick:")),
    BLUSH(EmojiParser.parseToUnicode(":blush:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
