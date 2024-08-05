package com.dp9v.hexlet_tg_bot.processors;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.utils.ChatPayload;
import com.dp9v.hexlet_tg_bot.utils.ChatState;
import com.dp9v.hexlet_tg_bot.utils.Status;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ErrorProcessor implements Processor{

    private final TgClient tgClient;

    @Override
    public Status getHandledStatus() {
        return Status.ERROR;
    }

    @Override
    public ChatState process(ChatPayload payload, Update update) {
        tgClient.sendMessage(
                "Произошла ошибка. \nВведите что-нибудь",
                update.getMessage().getChat().getId(),
                DefaultProcessor.BUTTONS);
        return new ChatState(Status.DEFAULT, new ChatPayload());
    }
}
