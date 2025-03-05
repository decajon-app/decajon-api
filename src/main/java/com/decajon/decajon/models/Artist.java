package com.decajon.decajon.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "artists")
public class Artist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column
    private String artist;

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
