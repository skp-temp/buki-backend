package com.example.skptemp.domain.badge.controller;

import com.example.skptemp.domain.badge.service.BadgeService;

import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityStaticUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/badge")
@RestController
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping("/status")
    public ResponseEntity<CustomResponse<Void>> getBadgeStatus(){
        Long userId = SecurityStaticUtil.getUserId();

        return null;
    }

    @GetMapping
    public ResponseEntity<CustomResponse<Void>> getAllBadges(){
        return null;
    }

    @Operation(description = "refreshBadgeStatus", summary = "badge 달성 정보 갱신")
    @PostMapping("/status")
    public ResponseEntity<CustomResponse<Void>> refreshBadgeStatus(){
        Long userId = SecurityStaticUtil.getUserId();
        return null;
    }
}
