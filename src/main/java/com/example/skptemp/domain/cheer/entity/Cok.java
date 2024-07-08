package com.example.skptemp.domain.cheer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "cok", indexes = {
        @Index(name = "user_and_charm_idx", columnList = "userId, charmId")
})
public class Cok {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long charmId;
    private LocalDate date;
}
