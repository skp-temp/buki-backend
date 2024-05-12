package com.example.skptemp.domain.cheer.service;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.domain.cheer.repository.CheerRepository;
import com.example.skptemp.global.common.SecurityStaticUtil;
import com.example.skptemp.global.common.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;

    public List<CheerCountResponse> getCheeringFriends() {


        return cheerRepository.getCheerCount(SecurityStaticUtil.getUserId());
    }

    public List<CheerCountResponse> getCheeredFriends() {
        return cheerRepository.getCheeredCount(SecurityStaticUtil.getUserId());
    }
}