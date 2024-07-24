package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.service.NotificationService;
import com.example.skptemp.domain.notification.util.NotificationUtil;
import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.FriendRelationshipService;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityStaticUtil;
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

    //TODO: 친구 추가 알림 전송 API
    @PostMapping("/request")
    ResponseEntity<CustomResponse<Void>> requestFriend(@RequestBody FriendRequestRequest request){
        User friendUser = userService.findByCode(request.getUserCode());
        Long userId = SecurityUtil.getUserId();
        // 친구 추가 알림 생성 로직 필요



        return null;
    }

    @Operation(summary = "createFriend", description = "친구 추가 API")
    @PostMapping
    ResponseEntity<CustomResponse<Void>> createFriend(@RequestBody FriendCreateRequest request){
        Long userId = SecurityUtil.getUserId();
        friendRelationshipService.enrollFriendRelationship(userId, request.getUserId());

        return ResponseEntity.ok(CustomResponse.ok());
    }

    @Operation(summary = "deleteFriend", description = "친구 삭제 API")
    @DeleteMapping("/{friend-id}")
    ResponseEntity<CustomResponse<Void>> deleteFriend(@RequestBody FriendDeleteRequest request){
        Long userId = SecurityUtil.getUserId();
        friendRelationshipService.deleteFriendRelationship(userId, request.getFriendId());
        return ResponseEntity.ok(CustomResponse.ok());
    }

    @Operation(summary = "친구 요청 전송 API")
    @PostMapping("/request")
    public ResponseEntity<CustomResponse<Void>> requestFriend(@RequestBody FriendCreateRequest request){
        User friendUser = userService.findByCode(request.getUserCode());
        UserResponse userResponse = userService.findById(SecurityStaticUtil.getUserId());
        notificationService.sendNotification(new NotificationRequest(friendUser.getId(), "친구 요청", NotificationUtil.FRIEND_REQUESTED_FORMAT.formatted(userResponse.getFirstName())));
        return ResponseEntity.ok(CustomResponse.ok());
    }

}
