package com.example.skptemp.domain.cheering.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CheeringMessage {

    @Id
    private UUID id;

    private String message;

    private Long fromUserId;
    private Long toUserId;

    private Long charmId;





}
