package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.service.FriendRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
@RestController
public class FriendController {
    private final FriendRelationshipService friendRelationshipService;
//
//    @GetMapping("/{user-id}")
//    ApiResponse<>
}
