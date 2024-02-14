package com.example.skptemp.domain.character.repository;

import com.example.skptemp.domain.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
