package com.clone.backend.linkedin.notification_service.consumer;

import com.clone.backend.linkedin.notification_service.client.ConnectionClient;
import com.clone.backend.linkedin.notification_service.entity.Notification;
import com.clone.backend.linkedin.notification_service.model.PersonDto;
import com.clone.backend.linkedin.notification_service.repository.NotificationRepository;
import com.clone.backend.linkedin.posts_service.event.PostCreatedEvent;
import com.clone.backend.linkedin.posts_service.event.PostLikedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceConsumer {

    private final NotificationRepository notificationRepository;
    private final ConnectionClient connectionClient;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(String postCreatedEventMessage) throws JsonProcessingException {
        PostCreatedEvent postCreatedEvent = objectMapper.readValue(postCreatedEventMessage, PostCreatedEvent.class);
        List<PersonDto> connections = connectionClient.getFirstConnections(postCreatedEvent.getCreatorId());
        for (PersonDto personDto : connections) {
            sendNotification(personDto.getUserId(), "Your connection " + postCreatedEvent.getCreatorId() + " has created a post");
        }
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(String postLikedEventMessage) throws JsonProcessingException {
        PostLikedEvent postLikedEvent = objectMapper.readValue(postLikedEventMessage, PostLikedEvent.class);
        String message = String.format("%s has liked your post", postLikedEvent.getLikedByUserId());
        sendNotification(postLikedEvent.getCreatorId(), message);
    }

    public void sendNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);
        notificationRepository.save(notification);
    }

}
