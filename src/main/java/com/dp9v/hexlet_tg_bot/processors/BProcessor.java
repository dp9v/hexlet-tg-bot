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
public class BProcessor implements Processor {
    private final TgClient tgClient;

    @Override
    public Status getHandledStatus() {
        return Status.ENTER_B;
    }

    @Override
    public ChatState process(ChatPayload payload, Update update) {
        int b = Integer.parseInt(update.getMessage().getText());
        var newPayload = new ChatPayload();
        newPayload.setA(payload.getA());
        newPayload.setB(b);
        tgClient.sendMessage("Hello!\nPlease enter C: ", update.getMessage().getChat().getId());
        return new ChatState(Status.ENTER_C, newPayload);
    }
}
