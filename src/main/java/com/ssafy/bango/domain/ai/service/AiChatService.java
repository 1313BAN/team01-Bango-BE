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
        try {
            var response = facilityChatClient.prompt()
                .user(address)
                .call();
            return response.content();
        } catch (Exception e) {
            log.error("AI 채팅 서비스 호출 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("AI 서비스 호출에 실패했습니다.", e);
        }
    }
}
