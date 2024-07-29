package com.dp9v.hexlet_tg_bot.processors;

import com.dp9v.hexlet_tg_bot.dto.Update;
import com.dp9v.hexlet_tg_bot.utils.ChatPayload;
import com.dp9v.hexlet_tg_bot.utils.ChatState;
import com.dp9v.hexlet_tg_bot.utils.Status;
import com.dp9v.hexlet_tg_bot.utils.TgClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CProcessor implements Processor {
    private final TgClient tgClient;

    @Override
    public Status getHandledStatus() {
        return Status.ENTER_C;
    }

    @Override
    public ChatState process(ChatPayload payload, Update update) {
        Long chatId = update.getMessage().getChat().getId();
        int c = Integer.parseInt(update.getMessage().getText());
        double[] result = solveQuadraticEquation(payload.getA(), payload.getB(), c);
        if (result == null) {
            tgClient.sendMessage("Уравнение имеет комплексные корни.", chatId);
        } else {
            tgClient.sendMessage("Решение уравнения: " + Arrays.toString(result), chatId);
        }
        tgClient.sendMessage("Enter something", chatId);
        return new ChatState(Status.DEFAULT, new ChatPayload());
    }

    private static double[] solveQuadraticEquation(double a, double b, double c) {
        // Вычисляем дискриминант
        double discriminant = b * b - 4 * a * c;

        // Если дискриминант отрицательный, уравнение имеет комплексные корни
        if (discriminant < 0) {
            return null;
        }

        // Если дискриминант равен нулю, уравнение имеет один корень
        if (discriminant == 0) {
            double root = -b / (2 * a);
            return new double[]{root};
        }

        // Если дискриминант положительный, уравнение имеет два корня
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double root1 = (-b + sqrtDiscriminant) / (2 * a);
        double root2 = (-b - sqrtDiscriminant) / (2 * a);
        return new double[]{root1, root2};
    }
}
