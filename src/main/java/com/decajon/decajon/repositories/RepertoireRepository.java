package com.decajon.decajon.repositories;

import com.decajon.decajon.dto.RepertoireSongCardDto;
import com.decajon.decajon.models.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long>
{
    /**
     * Query que busca las canciones pertenecientes a un id de grupo.
     * Regresa 3 columnas:
     *  - id de la tabla repertorio
     *  - nombre de la cancion (en la tabla songs)
     *  - nombre del artista (en caso de existir uno, de la tabla artistas)
     * Por lo tanto es un query que hace union de 3 tablas: repertoires, songs y artists
     * @param groupId
     * @return
     */
    @Query("SELECT new com.decajon.decajon.dto.RepertoireSongCardDto(r.id, s.title, a.artist) " +
            "FROM Repertoire r JOIN Song s ON r.song.id = s.id " +
            "LEFT JOIN Artist a ON s.artist.id = a.id " +
            "WHERE r.group.id = :groupId " +
            "ORDER BY s.title")
    List<RepertoireSongCardDto> findRepertoireSongsCardsByGroupId(@Param("groupId") Long groupId);
}
