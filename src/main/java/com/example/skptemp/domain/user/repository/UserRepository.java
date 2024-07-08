package com.example.skptemp.domain.user.repository;

import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.global.constant.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginTypeAndPlatformProviderIdAndIsValidIsTrue(LoginType loginType, String authProviderId);
    Optional<User> findByIdAndIsValidIsTrue(Long id);
    Optional<User> findByCodeAndIsValidIsTrue(String code);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.gachaEnable = false")
    void updateAllGachaStatusToFalse();
}
