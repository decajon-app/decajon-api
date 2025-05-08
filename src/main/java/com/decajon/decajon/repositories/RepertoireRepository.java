package com.decajon.decajon.repositories;

import com.decajon.decajon.dto.RepertoireSongCardDto;
import com.decajon.decajon.dto.RepertoireSongDto;
import com.decajon.decajon.models.Repertoire;
import com.decajon.decajon.projections.SuggestionCardProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT new com.decajon.decajon.dto.RepertoireSongDto(" +
            "r.id, " +
            "r.tone, " +
            "r.comment, " +
            "r.performance, " +
            "r.popularity, " +
            "r.complexity, " +
            "r.practicedAt, " +
            "s.title, " +
            "s.duration, " +
            "g.genre, " +
            "a.artist) " +
            "FROM Repertoire r " +
            "JOIN Song s ON r.song.id = s.id " +
            "LEFT JOIN Genre g ON s.genre.id = g.id " +
            "LEFT JOIN Artist a ON s.artist.id = a.id " +
            "WHERE r.id = :repertoireId")
    Optional<RepertoireSongDto> findSongDetailsByRepertoireId(@Param("repertoireId") Long repertoireId);

    @Query(value = "SELECT card FROM repertoires WHERE id = :id", nativeQuery = true)
    String findCardJsonById(@Param("id") Long id);

    @Query(
        value = """
            SELECT\s
                r.id AS repertoireId,
                s.title AS title,
                a.artist AS artist,
                r.performance AS performance,
                to_timestamp((r.card->>'due')::double precision) AS dueDate,
                g.name AS group
            FROM repertoires r
            JOIN songs s ON r.song_id = s.id
            JOIN artists a ON s.artist_id = a.id
            JOIN users_groups ug ON r.group_id = ug.group_id
            JOIN groups g ON g.id = r.group_id
            WHERE ug.user_id = :userId
              AND (r.card->>'due') IS NOT NULL
              AND to_timestamp((r.card->>'due')::double precision) BETWEEN now() AND now() + interval '7 days'
            ORDER BY dueDate ASC
        """,
        nativeQuery = true
    )
    List<SuggestionCardProjection> findSuggestionsByUserId(@Param("userId") Long userId);

    @Query(value = """
    SELECT\s
        r.id AS repertoireId,
        s.title AS title,
        a.artist AS artist,
        r.performance AS performance,
        to_timestamp((r.card->>'due')::double precision) AS dueDate,
        g.name AS group
    FROM repertoires r
    JOIN songs s ON r.song_id = s.id
    JOIN artists a ON s.artist_id = a.id
    JOIN groups g ON g.id = r.group_id
    WHERE g.id = :groupId
      AND (r.card->>'due') IS NOT NULL
      AND to_timestamp((r.card->>'due')::double precision) BETWEEN now() - interval '7 days' AND now() + interval '7 days'
    ORDER BY dueDate ASC
    """, nativeQuery = true)
    List<SuggestionCardProjection> findSuggestionsByGroupId(@Param("groupId") Long groupId);
}
