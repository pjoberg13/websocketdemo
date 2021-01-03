package peter.websocketdemo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import peter.websocketdemo.model.ChatMessage;
import peter.websocketdemo.model.MessageType;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectedEvent event) {
        log.info("new connection established");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final String username = (String) headerAccessor.getSessionAttributes().get("username");
        final ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.DISCONNECT)
                .sender(username)
                .build();
        sendingOperations.convertAndSend("/topic/public", chatMessage);
        log.info("connection lost");
    }
}
