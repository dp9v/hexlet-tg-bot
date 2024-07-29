package com.dp9v.hexlet_tg_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class TgClientConfig {
    private static final String TG_BOT_API_URL = "https://api.telegram.org/bot%s/";

    @Bean
    public RestTemplate tgRestTemplate(
            @Value("${tg.bot.timeout}") int timeout,
            @Value("${tg.bot.token}") String token,
            RestTemplateBuilder restTemplateBuilder
    ) {
        var timeoutDuration = Duration.of(timeout, ChronoUnit.SECONDS);
        return restTemplateBuilder
                .rootUri(TG_BOT_API_URL.formatted(token))
                .setReadTimeout(timeoutDuration)
                .setConnectTimeout(timeoutDuration)
                .build();
    }

}
