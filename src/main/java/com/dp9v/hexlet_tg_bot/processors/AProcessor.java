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
public class AProcessor implements Processor {
    private final TgClient tgClient;

    @Override
    public Status getHandledStatus() {
        return Status.ENTER_A;
    }

    @Override
    public ChatState process(ChatPayload payload, Update update) {
        int a = Integer.parseInt(update.getMessage().getText());
        var newPayload = new ChatPayload();
        newPayload.setA(a);
        tgClient.sendMessage("Hello!\nPlease enter B: ", update.getMessage().getChat().getId());
        return new ChatState(Status.ENTER_B, newPayload);
    }
}
