package com.dp9v.hexlet_tg_bot.services;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class TgMainProcessor {

    private final TgClient tgClient;

    public TgMainProcessor(TgClient tgClient) {
        this.tgClient = tgClient;
    }

    @SneakyThrows
    @KafkaListener(topics = "updates", groupId = "demo-group")
    public void onUpdate(
            @Payload Update update
    ) {
        tgClient.sendMessage(
                "Response: " + update.getMessage().getText(),
                update.getMessage().getChat().getId()
        );
    }
}
