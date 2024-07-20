package com.dp9v.hexlet_tg_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Message {
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String TEXT_FIELD = "text";

    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId;

    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;

    @JsonProperty(FROM_FIELD)
    private User from;

    @JsonProperty(DATE_FIELD)
    private Integer date;

    @JsonProperty(CHAT_FIELD)
    private Chat chat;

    @JsonProperty(TEXT_FIELD)
    private String text;
}
