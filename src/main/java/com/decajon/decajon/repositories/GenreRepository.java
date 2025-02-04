package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>
{
    List<Genre> findByGroupId(Long groupId);
}
