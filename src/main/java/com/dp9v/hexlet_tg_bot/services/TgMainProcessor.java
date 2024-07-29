package com.dp9v.hexlet_tg_bot.services;

import com.dp9v.hexlet_tg_bot.dto.Update;
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

@Service
public class TgMainProcessor {

    private final TgClient tgClient;
    private final ChatStateRepository chatStateRepository;

    public TgMainProcessor(TgClient tgClient, ChatStateRepository chatStateRepository) {
        this.tgClient = tgClient;
        this.chatStateRepository = chatStateRepository;
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
            switch (state.getStatus()) {
                case DEFAULT -> {
                    tgClient.sendMessage("Hello!\nPlease enter A: ", chatId);
                    nextState = new ChatState(Status.ENTER_A, new ChatPayload());
                }
                case ENTER_A -> {
                    int a = Integer.parseInt(update.getMessage().getText());
                    var newPayload = new ChatPayload();
                    newPayload.setA(a);
                    tgClient.sendMessage("Hello!\nPlease enter B: ", chatId);
                    nextState = new ChatState(Status.ENTER_B, newPayload);
                }
                case ENTER_B -> {
                    int b = Integer.parseInt(update.getMessage().getText());
                    var newPayload = new ChatPayload();
                    newPayload.setA(state.getPayload().getA());
                    newPayload.setB(b);
                    tgClient.sendMessage("Hello!\nPlease enter C: ", chatId);
                    nextState = new ChatState(Status.ENTER_C, newPayload);
                }
                case ENTER_C -> {
                    int c = Integer.parseInt(update.getMessage().getText());
                    double[] result = solveQuadraticEquation(state.getPayload().getA(), state.getPayload().getB(), c);
                    if (result == null) {
                        tgClient.sendMessage("Уравнение имеет комплексные корни.", chatId);
                    } else {
                        tgClient.sendMessage("Решение уравнения: " + Arrays.toString(result), chatId);
                    }
                    tgClient.sendMessage("Enter something", chatId);
                    nextState = new ChatState(Status.DEFAULT, new ChatPayload());
                }
                default -> throw new RuntimeException("Unknown state");
            }
        } catch (Exception exception) {
            tgClient.sendMessage("Произошла ошибка. \nВведите что-нибудь", chatId);
            nextState = new ChatState(Status.DEFAULT, new ChatPayload());
        }
        chatStateRepository.updateState(chatId, nextState);
    }

    // Метод для решения квадратного уравнения
    public static double[] solveQuadraticEquation(double a, double b, double c) {
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
