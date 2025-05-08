package com.decajon.decajon.models;

import lombok.*;

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
    private double stability;
    private double difficulty;
    private OffsetDateTime due;
    private OffsetDateTime last_review;

    @Override
    public String toString() {
        return "CardData{" +
                "cardId=" + card_id +
                ", state=" + state +
                ", step=" + step +
                ", stability=" + stability +
                ", difficulty=" + difficulty +
                ", due=" + due +
                ", last_review=" + last_review +
                '}';
    }
}
