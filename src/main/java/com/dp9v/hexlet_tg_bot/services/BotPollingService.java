package com.dp9v.hexlet_tg_bot.services;

import com.dp9v.hexlet_tg_bot.dto.Message;
import com.dp9v.hexlet_tg_bot.dto.ResponseMessage;
import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.dto.UpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BotPollingService {
    private static final AtomicInteger offset = new AtomicInteger();

    private final RestTemplate tgClient;

    @Scheduled(fixedDelay = 2000)
    public void poll() {
        ResponseEntity<UpdateResponse> updates = tgClient.getForEntity(
                "/getUpdates?offset=" + offset.get(),
                UpdateResponse.class
        );
        for (Update update : updates.getBody().getResult()) {
            var response = new ResponseMessage(
                    update.getMessage().getChat().getId(),
                    "Response: " + update.getMessage().getText()
            );
            var res = tgClient.postForEntity("/sendMessage", response, Message.class);

            if (res.getStatusCode() == HttpStatusCode.valueOf(200)) {
                offset.set(update.getUpdateId() + 1);
            }
        }
    }
}
