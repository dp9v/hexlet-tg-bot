package com.dp9v.hexlet_tg_bot.processors;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProcessor implements Processor {
    public final static List<String> BUTTONS = List.of(Buttons.SOLVE_EQ, Buttons.SEND_IMG);

    private final TgClient tgClient;

    @Override
    public Status getHandledStatus() {
        return Status.DEFAULT;
    }

    @Override
    public ChatState process(ChatPayload payload, Update update) {
        String responseText = update.getMessage().getText();
        switch (responseText) {
            case Buttons.SOLVE_EQ:
                tgClient.sendMessage(
                        "Hello!\nPlease enter A: ",
                        update.getMessage().getChat().getId()
                );
                return new ChatState(Status.ENTER_A, new ChatPayload());
            case Buttons.SEND_IMG:
                tgClient.sendImage("/home/dmitrii/Programming/Java/rm/hexlet-tg-bot/src/main/resources/lena.png",
                        update.getMessage().getChat().getId(),
                        BUTTONS);
                return new ChatState(Status.DEFAULT, new ChatPayload());
            default:
                throw new RuntimeException("Неизвестная команда");
        }

    }
}
