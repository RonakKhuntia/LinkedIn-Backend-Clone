package com.clone.backend.linkedin.notification_service.notification_service.repository;

import com.clone.backend.linkedin.notification_service.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}