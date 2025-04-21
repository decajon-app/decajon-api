package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>
{
    List<Artist> findByGroupId(Long groupId);

    Optional<Artist> findByGroupIdAndArtistIgnoreCase(Long groupId, String artist);
}
