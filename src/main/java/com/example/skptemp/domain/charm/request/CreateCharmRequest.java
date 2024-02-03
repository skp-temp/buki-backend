package com.example.skptemp.domain.charm.request;

public record CreateCharmRequest(
        Long categoryId,
        String goal
        ) {
}
