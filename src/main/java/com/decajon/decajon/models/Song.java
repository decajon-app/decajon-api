package com.decajon.decajon.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "songs")
public class Song
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "artist_id")
    private Long artistId;

    @Column
    private String title;

    @Column
    private int duration;

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
