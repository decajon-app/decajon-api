package com.decajon.decajon.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "repertoires")
public class Repertoire
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;

    @Column(length = 30)
    private String tone;

    @Column
    private String comment;

    @Column
    private int performance;

    @Column
    private Long popularity;

    @Column
    private int complexity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "card", columnDefinition = "jsonb", nullable = false)
    private CardData card;

    @Column(name = "practiced_at")
    private LocalDateTime practicedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist()
    {
        if (createdAt == null)
        {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null)
        {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate()
    {
        updatedAt = LocalDateTime.now();
    }
}
