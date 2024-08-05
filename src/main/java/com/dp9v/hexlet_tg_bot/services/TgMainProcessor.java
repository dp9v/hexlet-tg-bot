package com.dp9v.hexlet_tg_bot.services;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.processors.Processor;
import com.dp9v.hexlet_tg_bot.repositories.ChatStateRepository;
import com.dp9v.hexlet_tg_bot.utils.ChatPayload;
import com.dp9v.hexlet_tg_bot.utils.ChatState;
import com.dp9v.hexlet_tg_bot.utils.Status;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TgMainProcessor {

    private static final Logger log = LoggerFactory.getLogger(TgMainProcessor.class);
    private final ChatStateRepository chatStateRepository;
    private final Map<Status, Processor> processors;

    public TgMainProcessor(ChatStateRepository chatStateRepository, List<Processor> processors) {
        this.chatStateRepository = chatStateRepository;
        this.processors = processors.stream()
                .collect(Collectors.toMap(Processor::getHandledStatus, Function.identity()));

        if (!this.processors.containsKey(Status.DEFAULT) || !this.processors.containsKey(Status.ERROR)) {
            throw new RuntimeException("No processors for required statuses.");
        }
    }

    @SneakyThrows
    @KafkaListener(topics = "updates", groupId = "demo-group")
    public void onUpdate(
            @Payload Update update
    ) {
        Long chatId = update.getMessage().getChat().getId();
        ChatState state = chatStateRepository.getState(chatId);
        ChatState nextState = null;
        System.out.printf("Teкущий статус чата %d: %s%n", chatId, state.getStatus());

        try {
            nextState = processors.get(state.getStatus()).process(state.getPayload(), update);
        } catch (Exception exception) {
            log.error("Some err", exception);
            nextState = processors.get(Status.ERROR).process(state.getPayload(), update);
        }
        chatStateRepository.updateState(chatId, nextState);
    }
}

