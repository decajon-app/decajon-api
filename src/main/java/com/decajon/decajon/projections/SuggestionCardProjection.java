package com.decajon.decajon.projections;

import java.time.Instant;

public interface SuggestionCardProjection
{
    Long getRepertoireId();
    String getTitle();
    String getArtist();
    String getGroup();
    short getPerformance();
    Instant getDueDate();
}
