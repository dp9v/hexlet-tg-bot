package com.dp9v.hexlet_tg_bot.utils;

import com.dp9v.hexlet_tg_bot.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TgClient {

    private final RestTemplate tgRestTemplate;

    public List<Update> getUpdates(long offset) {
        var updates = tgRestTemplate.getForEntity("/getUpdates?offset=" + offset, UpdateResponse.class);
        if (updates.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("getUpdates failed with status: " + updates.getStatusCode());
        }
        return updates.getBody().getResult();
    }

    public void sendMessage(String msg, Long chatId) {
        sendMessage(msg, chatId, List.of());
    }

    public void sendMessage(String msg, Long chatId, List<String> buttons) {
        var response = new ResponseMessage(chatId, msg, buildKeyboard(buttons));
        tgRestTemplate.postForEntity("/sendmessage", response, String.class);
    }

    private ReplyKeyboardMarkup buildKeyboard(List<String> buttons) {
        return new ReplyKeyboardMarkup(
                List.of(buttons.stream().map(KeyboardButton::new).toList()),
                true,
                true
        );
    }
}
