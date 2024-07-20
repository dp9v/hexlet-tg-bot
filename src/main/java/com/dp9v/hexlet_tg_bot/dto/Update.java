package com.dp9v.hexlet_tg_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Update {
    private static final String UPDATEID_FIELD = "update_id";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(UPDATEID_FIELD)
    private Integer updateId;

    /**
     * Optional.
     * New incoming message of any kind — text, photo, sticker, etc.
     */
    @JsonProperty(MESSAGE_FIELD)
    private Message message;

}