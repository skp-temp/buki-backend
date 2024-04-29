package com.example.skptemp.domain.cheer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheerRepository extends JpaRepository<Cheer, Long>,CheerCustomRepository {

}