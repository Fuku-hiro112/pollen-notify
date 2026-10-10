package com.fuku.pollen_notify.client;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

@Component
public class DiscordNotificationSender implements NotificationSender {

    private final RestClient restClient;

    public DiscordNotificationSender(@Value("${discord.webhook-url}") String webhookUrl) {
        this.restClient = RestClient.create(webhookUrl);
    }

    @Override
    public void send(String message) {
        restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(/* ★ Discord Webhookが期待するJSONの形を作る */)//TODO: Discord Webhookが期待するJSONの形を検索し、わたす
                .retrieve()
                .toBodilessEntity();
    }
}
