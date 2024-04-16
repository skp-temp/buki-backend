package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.FriendRelationshipService;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.ApiResponse;
import com.example.skptemp.global.common.SecurityUtil;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
@RestController
public class FriendController {
    private final FriendRelationshipService friendRelationshipService;
    private final UserService userService;
    private final SecurityUtil securityUtil;

    @Operation(summary = "getFriendList", description = "사용자 친구 리스트 조회 API") //TODO: 친구의 부적 정보 같이 전달해야
    @GetMapping("/{user-id}")
    ResponseEntity<ApiResponse<FriendResponse>> getFriendList(@Valid Long userId){
        List<FriendResult> friendRelationshipList = friendRelationshipService.findFriendRelationshipList(userId);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(new FriendResponse(friendRelationshipList)));
    }

    @Operation(summary = "createFriend", description = "친구 추가 API")
    @PostMapping
    ResponseEntity<ApiResponse<Void>> createFriend(@RequestBody FriendCreateRequest request){
        Long userId = securityUtil.getUserIdFromContext();
        UserResponse userResponse = userService.findById(userId);

        // 자기 자신과 친구 관계 생성 불가.
        if(userResponse.getCode().equals(request.getUserCode())){
            throw new GlobalException(GlobalErrorCode.FRIEND_RELATIONSHIP_VALID_EXCEPTION);
        }
        User friendUser = userService.findByCode(request.getUserCode());

        friendRelationshipService.enrollFriendRelationship(userId, friendUser.getId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "deleteFriend", description = "친구 삭제 API")
    @DeleteMapping("/{friend-id}")
    ResponseEntity<ApiResponse<Void>> deleteFriend(@RequestBody FriendDeleteRequest request){
        Long userId = securityUtil.getUserIdFromContext();
        friendRelationshipService.deleteFriendRelationship(userId, request.getFriendId());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "getFriendsCount", description = "친구 수 조회 API")
    @GetMapping("/count")
    ResponseEntity<ApiResponse<Integer>> getFriendsCount(Long userId){
        int friendsCount = friendRelationshipService.getFriendsCount(userId);
        return ResponseEntity.ok(ApiResponse.ok(friendsCount));
    }

}
