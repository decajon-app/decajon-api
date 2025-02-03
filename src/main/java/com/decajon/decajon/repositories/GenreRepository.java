package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>
{
    List<Genre> findByGroupId(Long groupId);
}
