package com.dp9v.hexlet_tg_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReplyKeyboardMarkup {
    @JsonProperty("keyboard")
    private List<List<KeyboardButton>> keyboard;


    @JsonProperty("resize_keyboard")
    private boolean resizeKeyboard;

    @JsonProperty("one_time_keyboard")
    private boolean oneTimeKeyboard;

}
