package com.decajon.decajon.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rehearsals")
@Data
public class Rehearsal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id", nullable = false)
    private Integer groupId;

    @Column(length = 15, nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'PENDING'")
    private String pending = "PENDING";

    @Column(name = "schedulet_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "rehearsals_songs",
            joinColumns = @JoinColumn(name = "rehearsal_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs = new ArrayList<>();
}
