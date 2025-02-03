package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long>
{
    List<Artist> findByGroupId(Long groupId);
}
