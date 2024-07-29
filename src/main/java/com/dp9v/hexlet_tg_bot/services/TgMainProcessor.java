package com.dp9v.hexlet_tg_bot.services;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.processors.Processor;
import com.dp9v.hexlet_tg_bot.repositories.ChatStateRepository;
import com.dp9v.hexlet_tg_bot.utils.ChatPayload;
import com.dp9v.hexlet_tg_bot.utils.ChatState;
import com.dp9v.hexlet_tg_bot.utils.Status;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.SneakyThrows;
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

    private final TgClient tgClient;
    private final ChatStateRepository chatStateRepository;
    private final Map<Status, Processor> processors;

    public TgMainProcessor(TgClient tgClient, ChatStateRepository chatStateRepository, List<Processor> processors) {
        this.tgClient = tgClient;
        this.chatStateRepository = chatStateRepository;
        this.processors = processors.stream()
                .collect(Collectors.toMap(Processor::getHandledStatus, Function.identity()));
    }

    @SneakyThrows
    @KafkaListener(topics = "updates", groupId = "demo-group")
    public void onUpdate(
            @Payload Update update
    ) {
        Long chatId = update.getMessage().getChat().getId();
        ChatState state = chatStateRepository.getState(chatId);
        ChatState nextState = null;

        try {
            nextState = processors.get(state.getStatus()).process(state.getPayload(), update);
        } catch (Exception exception) {
            tgClient.sendMessage("Произошла ошибка. \nВведите что-нибудь", chatId);
            nextState = new ChatState(Status.DEFAULT, new ChatPayload());
        }
        chatStateRepository.updateState(chatId, nextState);
    }
}
