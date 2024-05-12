package com.example.skptemp.domain.cheer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Getter
@NoArgsConstructor
public class Cheer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheer_id", nullable = false)
    private Long id;
    @NotNull
    private Long from_user;
    @NotNull
    private Long to_user;
    @NotNull
    private String message;


    @PersistenceCreator
    public Cheer(Long from_user, Long to_user, String message) {
        this.from_user = from_user;
        this.to_user = to_user;
        this.message = message;
    }
}
