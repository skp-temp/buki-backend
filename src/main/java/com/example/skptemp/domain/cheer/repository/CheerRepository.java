package com.example.skptemp.domain.cheer.repository;

import com.example.skptemp.domain.cheer.entity.Cheer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheerRepository extends JpaRepository<Cheer, Long>,CheerCustomRepository {

}