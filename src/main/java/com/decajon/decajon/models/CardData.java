package com.decajon.decajon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardData
{
    private Long card_id;
    private Integer state;
    private Integer step;
    private Integer stability;
    private Integer difficulty;
    private OffsetDateTime due;
    private OffsetDateTime lastReview;

    @Override
    public String toString() {
        return "CardData{" +
                "cardId=" + card_id +
                ", state=" + state +
                ", step=" + step +
                ", stability=" + stability +
                ", difficulty=" + difficulty +
                ", due=" + due +
                ", lastReview=" + lastReview +
                '}';
    }
}
