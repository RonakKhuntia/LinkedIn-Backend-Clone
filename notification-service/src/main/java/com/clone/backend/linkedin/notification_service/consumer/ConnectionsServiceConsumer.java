package com.clone.backend.linkedin.notification_service.consumer;

import com.clone.backend.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.clone.backend.linkedin.connections_service.event.SendConnectionRequestEvent;
import com.clone.backend.linkedin.notification_service.service.SendNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsServiceConsumer {

    private final SendNotification sendNotification;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(String sendConnectionRequestEventMessage) throws JsonProcessingException {
        SendConnectionRequestEvent sendConnectionRequestEvent = objectMapper.readValue(sendConnectionRequestEventMessage,
                SendConnectionRequestEvent.class);
        log.info("handle connections: handleSendConnectionRequest: {}", sendConnectionRequestEvent);
        String message =
                "You have received a connection request from user with id: %d"+sendConnectionRequestEvent.getSenderId();
        sendNotification.send(sendConnectionRequestEvent.getReceiverId(), message);
    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(String acceptConnectionRequestEventMessage) throws JsonProcessingException {
        AcceptConnectionRequestEvent acceptConnectionRequestEvent = objectMapper.readValue(acceptConnectionRequestEventMessage,
                AcceptConnectionRequestEvent.class);
        log.info("handle connections: handleAcceptConnectionRequest: {}", acceptConnectionRequestEvent);
        String message =
                "Your connection request has been accepted by the user with id: %d"+acceptConnectionRequestEvent.getReceiverId();
        sendNotification.send(acceptConnectionRequestEvent.getSenderId(), message);
    }

}
