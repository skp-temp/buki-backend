package com.example.skptemp.global.util;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityManagerUtil {

    private final EntityManager entityManager;

    public <T> T getReference(Class<T> clazz, Long id) {
        return entityManager.getReference(clazz, id);
    }
}
