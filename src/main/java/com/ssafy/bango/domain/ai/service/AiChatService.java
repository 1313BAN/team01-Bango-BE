package com.ssafy.bango.domain.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiChatService {

    private final ChatClient facilityChatClient;

    public Object facilityChat(String address) {
        var response = facilityChatClient.prompt()
            .user("도로명 주소: " + address)
            .call();
        return response.content();
    }
}
