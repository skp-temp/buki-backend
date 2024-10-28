package com.example.skptemp.domain.badge.controller;

import com.example.skptemp.domain.badge.dto.BadgeResult;
import com.example.skptemp.domain.badge.dto.GetAllBadgesResponse;
import com.example.skptemp.domain.badge.dto.GetBadgeStatusResponse;
import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import com.example.skptemp.domain.badge.service.BadgeService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/badges")
@RestController
public class BadgeController {
    private final BadgeService badgeService;

    @Operation(description = "사용자가 현재 보유 중인 뱃지 상태 정보를 조회합니다.", summary = "GetBadgeStatus")
    @GetMapping("/status")
    public ResponseEntity<CustomResponse<GetBadgeStatusResponse>> getBadgeStatus(){
        Long userId = SecurityUtil.getUserId();
        List<UserBadgeResult> resultList = badgeService.getUserBadgeInfo(userId);

        var response = new GetBadgeStatusResponse(resultList);

        return ResponseEntity.ok(CustomResponse.ok(response));
    }

    @Operation(description = "부키 서비스의 전체 뱃지 정보를 조회합니다.", summary = "GetAllBadges")
    @GetMapping
    public ResponseEntity<CustomResponse<GetAllBadgesResponse>> getAllBadges(){
        List<BadgeResult> list = badgeService.getAllBadges();
        var response = new GetAllBadgesResponse(list);

        return ResponseEntity.ok(CustomResponse.ok(response));
    }

    @Operation(description = "사용자의 뱃지 획득 정보를 갱신합니다.", summary = "RefreshBadgeStatus")
    @PostMapping("/status")
    public ResponseEntity<CustomResponse<Void>> refreshBadgeStatus(){
        Long userId = SecurityUtil.getUserId();
        badgeService.refreshUserBadgeInfo(userId);

        return ResponseEntity.ok(CustomResponse.ok());
    }
}
