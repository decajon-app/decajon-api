package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long>
{
    // Esta linea genera el query con el JOIN entre la tabla
    // repertoires y songs para recuperar todas las canciones
    // de un grupo
    @Query("SELECT r FROM Repertoire r JOIN Song s ON r.songId = s.id WHERE s.groupId = :groupId")
    List<Repertoire> findBySong_GroupId(@Param("groupId") Long groupId);
}
