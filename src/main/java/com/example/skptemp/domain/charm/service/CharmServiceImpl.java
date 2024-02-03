package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.character.entity.Character;
import com.example.skptemp.domain.character.repository.CharacterRepository;
import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.ChallengeHistoryRepository;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CharmServiceImpl implements CharmService {

    private final CharmRepository charmRepository;
    private final CharacterRepository characterRepository;
    private final ChallengeHistoryRepository challengeHistoryRepository;


    @Override
    @Transactional
    public void createCharm(CreateCharmRequest request) {
        Character character = characterRepository.save(new Character());
        Charm charm = Charm.builder()
                .categoryId(request.categoryId())
                .goal(request.goal())
                .characterId(character.getId())
                .build();
        charmRepository.save(charm);
    }

    @Override
    @Transactional
    public void dailyGoalDone(Long charmId, Long userId) {

        LocalDate date = LocalDate.now();

        ChallengeHistory challengeHistory = ChallengeHistory.builder()
                .historyDate(date)
                .userId(userId)
                .charmId(charmId)
                .build();
        challengeHistoryRepository.save(challengeHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public CharmDetailResponse getCharm(Long charmId) {
        Long userId = null;

        return charmRepository.getDetail(charmId, userId);

    }

    @Override
    public void updateCharm(Long charmId) {


    }

    @Override
    public void updateCharmSetting(Long charmId, CharmSettingUpdateRequest request) {

        charmRepository.settingUpdate(charmId, request);
    }
}
