package com.decajon.decajon.models;

import jakarta.persistence.*;
import jakarta.validation.valueextraction.UnwrapByDefault;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users_songs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "group_id", "song_id"})
})
public class UserSong
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "song_id", nullable = false)
    private Long songId;

    @Column(nullable = false)
    private int performance = 0;

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
