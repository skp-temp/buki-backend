package com.example.skptemp.domain.cheer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Cheer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheer_id", nullable = false)
    private Long id;
    @NotNull
    private Long from;
    @NotNull
    private Long to;
    @NotNull
    private String message;


}
