package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>
{
    List<Song> findByGroupId(Long groupId);
}
