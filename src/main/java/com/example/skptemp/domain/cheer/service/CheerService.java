package com.example.skptemp.domain.cheer.service;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.domain.cheer.repository.CheerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;

    public List<CheerCountResponse> getCheeringFriends(boolean isDesc) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = Long.parseLong(userDetails.getUsername());

        return cheerRepository.getCheerCount(isDesc, userId);
    }
}
