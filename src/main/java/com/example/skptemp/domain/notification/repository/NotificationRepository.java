package com.example.skptemp.domain.notification.repository;

import com.example.skptemp.domain.notification.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> ,NotificationCustomRepository{

}