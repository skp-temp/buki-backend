package com.example.skptemp.domain.charm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ItemCharmRequest {

    private List<Long> itemIdList;

    private Long charmId;

}
