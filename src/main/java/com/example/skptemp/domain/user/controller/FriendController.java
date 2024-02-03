package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.dto.FriendRequest;
import com.example.skptemp.domain.user.dto.FriendResponse;
import com.example.skptemp.domain.user.dto.FriendResult;
import com.example.skptemp.domain.user.service.FriendRelationshipService;
import com.example.skptemp.global.common.ApiResponse;
import com.example.skptemp.global.common.SecurityUtil;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
@RestController
public class FriendController {
    private final FriendRelationshipService friendRelationshipService;
    private final SecurityUtil securityUtil;

    @Operation(summary = "getFriendList", description = "친구 조회 API")
    @GetMapping("/{user-id}")
    ApiResponse<FriendResponse> getFriendList(@Valid Long userId){
        List<FriendResult> friendRelationshipList = friendRelationshipService.findFriendRelationshipList(userId);
        return new ApiResponse<>(new FriendResponse(friendRelationshipList));
    }

    @Operation(summary = "createFriend", description = "친구 추가 API")
    @PostMapping
    ApiResponse<Void> createFriend(@RequestBody FriendRequest request){
        Long userId = securityUtil.getUserIdFromContext();

        // 본인과는 친구를 맺을 수 없습니다.
        if(userId.equals(request.getFriendId())){
            throw new GlobalException(GlobalErrorCode.FRIEND_RELATIONSHIP_VALID_EXCEPTION);
        }

        friendRelationshipService.enrollFriendRelationship(userId, request.getFriendId());
        return ApiResponse.ok();
    }

    @Operation(summary = "deleteFriend", description = "친구 삭제 API")
    @DeleteMapping("/{friend-id}")
    ApiResponse<Void> deleteFriend(@RequestBody FriendRequest request){
        Long userId = securityUtil.getUserIdFromContext();
        friendRelationshipService.deleteFriendRelationship(userId, request.getFriendId());
        return ApiResponse.ok();
    }

}
