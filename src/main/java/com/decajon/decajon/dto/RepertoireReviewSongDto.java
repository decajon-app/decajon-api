package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotNull;

public record RepertoireReviewSongDto
(
    @NotNull
    Long repertoireId,
    @NotNull
    int rating
) {}
