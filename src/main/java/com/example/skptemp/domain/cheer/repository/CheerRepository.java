package com.example.skptemp.domain.cheer.repository;

import com.example.skptemp.domain.charm.dto.CheerMessageResponse;
import com.example.skptemp.domain.cheer.entity.Cheer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheerRepository extends JpaRepository<Cheer, Long>, CheerCustomRepository {

    List<Cheer> findByCharmId(Long charmId);

    @Query("""
            SELECT user.firstName as name, cheer.message as message,cheer.createdAt as time  FROM Cheer cheer
            INNER JOIN User user ON cheer.fromUser = user.id
            WHERE cheer.charmId = :charmId
                """)
    List<CheerMessageResponse> findCheerMessage(@Param("charmId") Long charmId);

}