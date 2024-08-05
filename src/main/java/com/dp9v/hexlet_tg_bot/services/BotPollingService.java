package com.dp9v.hexlet_tg_bot.services;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BotPollingService {

    private static final AtomicInteger offset = new AtomicInteger();

    private final TgClient tgClient;

    private final KafkaTemplate<String, Update> kafkaTemplate;

    @SneakyThrows
    @Scheduled(fixedDelay = 2000)
    public void poll() {
        var updates = tgClient.getUpdates(offset.get());
        for (Update update : updates) {
            System.out.println(update);
            kafkaTemplate.send("updates", update.getUpdateId().toString(), update);
            offset.set(update.getUpdateId() + 1);
        }
    }
}
