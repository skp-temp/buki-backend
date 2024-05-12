package com.example.skptemp.domain.cheer.service;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.domain.cheer.repository.CheerRepository;
import com.example.skptemp.global.common.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;
    private final SecurityUtil securityUtil;

    public List<CheerCountResponse> getCheeringFriends(boolean isDesc) {
        Long userId = securityUtil.getUserIdFromContext();
        return cheerRepository.getCheerCount(isDesc, userId);
    }
}
