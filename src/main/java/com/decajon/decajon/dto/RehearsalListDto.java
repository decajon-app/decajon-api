package com.decajon.decajon.dto;

import java.util.List;

/**
 * Encapsula una lista de ensayos
 * @param rehearsals
 */
public record RehearsalListDto(
        List<RehearsalDto> rehearsals
) {}