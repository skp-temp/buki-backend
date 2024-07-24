package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.service.NotificationService;
import com.example.skptemp.domain.notification.util.NotificationUtil;
import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.FriendRelationshipService;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
@RestController
public class FriendController {
    private final FriendRelationshipService friendRelationshipService;
    private final NotificationService notificationService;
    private final UserService userService;

    @Operation(summary = "getFriendList", description = "사용자 친구 리스트 조회 API")
    @GetMapping
    ResponseEntity<CustomResponse<FriendResponse>> getFriendList(){
        Long userId = SecurityUtil.getUserId();
        List<FriendResult> friendRelationshipList = friendRelationshipService.findFriendRelationshipList(userId);

        return ResponseEntity.ok()
                .body(CustomResponse.ok(new FriendResponse(friendRelationshipList)));
    }

    @Operation(summary = "createFriend", description = "친구 수락 API 내가 요청한 사람의 친구 요청을 수락함 ")
    @PostMapping
    ResponseEntity<CustomResponse<Void>> createFriend(@RequestBody FriendCreateRequest request){
        Long userId = SecurityUtil.getUserId();
        friendRelationshipService.enrollFriendRelationship(userId, request.getUserId());
        UserResponse friendUser = userService.findByUserId(request.getUserId());
        notificationService.sendNotification(new NotificationRequest(friendUser.getUserId(), "친구 수락", NotificationUtil.FRIEND_ACCEPTED_FORMAT));
        return ResponseEntity.ok(CustomResponse.ok());
    }

    @Operation(summary = "deleteFriend", description = "친구 삭제 API")
    @DeleteMapping("/{friend-id}")
    ResponseEntity<CustomResponse<Void>> deleteFriend(@RequestBody FriendDeleteRequest request){
        Long userId = SecurityUtil.getUserId();
        friendRelationshipService.deleteFriendRelationship(userId, request.getFriendId());
        return ResponseEntity.ok(CustomResponse.ok());
    }

    @Operation(summary = "친구 요청 전송 API",description = "내가 user Code로 친구 요청을 보냄 ")
    @PostMapping("/request")
    public ResponseEntity<CustomResponse<Void>> requestFriend(@RequestBody FriendSendRequest request){
        User friendUser = userService.findByCode(request.getUserCode());
        notificationService.sendNotification(new NotificationRequest(friendUser.getId(), "친구 요청", NotificationUtil.FRIEND_REQUESTED_FORMAT));
        return ResponseEntity.ok(CustomResponse.ok());
    }

}
