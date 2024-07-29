package com.dp9v.hexlet_tg_bot.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatState {
    private Status status;
    private ChatPayload payload;
}
