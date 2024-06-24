package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.FriendRelationshipService;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityStaticUtil;
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

    @Operation(summary = "getFriendList", description = "사용자 친구 리스트 조회 API")
    @GetMapping("/{user-id}")
    ResponseEntity<CustomResponse<FriendResponse>> getFriendList(@Valid Long userId){
        List<FriendResult> friendRelationshipList = friendRelationshipService.findFriendRelationshipList(userId);

        return ResponseEntity.ok()
                .body(CustomResponse.ok(new FriendResponse(friendRelationshipList)));
    }

    @Operation(summary = "createFriend", description = "친구 추가 API")
    @PostMapping
    ResponseEntity<CustomResponse<Void>> createFriend(@RequestBody FriendCreateRequest request){
        Long userId = SecurityStaticUtil.getUserId();
        UserResponse userResponse = userService.findByUserId(userId);

        // 자기 자신과 친구 관계 생성 불가.
        if(userResponse.getCode().equals(request.getUserCode())){
            throw new GlobalException(GlobalErrorCode.FRIEND_RELATIONSHIP_VALID_EXCEPTION);
        }
        User friendUser = userService.findByCode(request.getUserCode());

        friendRelationshipService.enrollFriendRelationship(userId, friendUser.getId());
        return ResponseEntity.ok(CustomResponse.ok());
    }

    @Operation(summary = "deleteFriend", description = "친구 삭제 API")
    @DeleteMapping("/{friend-id}")
    ResponseEntity<CustomResponse<Void>> deleteFriend(@RequestBody FriendDeleteRequest request){
        Long userId = SecurityStaticUtil.getUserId();
        friendRelationshipService.deleteFriendRelationship(userId, request.getFriendId());
        return ResponseEntity.ok(CustomResponse.ok());
    }

}
