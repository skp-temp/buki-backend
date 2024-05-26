package com.example.skptemp.domain.cheer.entity;

import com.example.skptemp.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Getter
@NoArgsConstructor
public class Cheer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheer_id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "from_user")
    private Long fromUser;
    @NotNull
    @Column(name = "to_user")
    private Long toUser;
    @NotNull
    private String message;

    @NotNull
    private Long charmId;


    @PersistenceCreator
    public Cheer(Long fromUser, Long toUser, String message, Long charmId) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
        this.charmId = charmId;
    }
}
