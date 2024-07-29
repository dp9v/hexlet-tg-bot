package com.dp9v.hexlet_tg_bot.processors;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.utils.ChatPayload;
import com.dp9v.hexlet_tg_bot.utils.ChatState;
import com.dp9v.hexlet_tg_bot.utils.Status;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProcessor implements Processor {
    private final TgClient tgClient;

    @Override
    public Status getHandledStatus() {
        return Status.DEFAULT;
    }

    @Override
    public ChatState process(ChatPayload payload, Update update) {
        tgClient.sendMessage("Hello!\nPlease enter A: ", update.getMessage().getChat().getId());
        return new ChatState(Status.ENTER_A, new ChatPayload());
    }
}
