package com.dp9v.hexlet_tg_bot.utils;

import com.dp9v.hexlet_tg_bot.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

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

    public void sendImage(String pathToImage, Long chatId, List<String> buttons) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("chat_id", chatId);
        body.add("photo", new FileSystemResource(pathToImage));
        body.add("reply_markup", buildKeyboard(buttons));
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        tgRestTemplate.postForEntity("/sendPhoto", request, String.class);
    }

    private ReplyKeyboardMarkup buildKeyboard(List<String> buttons) {
        return new ReplyKeyboardMarkup(
                List.of(buttons.stream().map(KeyboardButton::new).toList()),
                true,
                true
        );
    }
}
