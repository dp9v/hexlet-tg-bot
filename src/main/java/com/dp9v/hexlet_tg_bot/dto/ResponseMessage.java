package com.dp9v.hexlet_tg_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseMessage {
    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("reply_markup")
    private ReplyKeyboardMarkup replyMarkup;

}
