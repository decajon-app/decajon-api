package com.decajon.decajon.services;

import com.decajon.decajon.dto.*;
import com.decajon.decajon.mappers.RepertoireMapper;
import com.decajon.decajon.models.*;
import com.decajon.decajon.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class RepertoireService
{
    private final RepertoireRepository repertoireRepository;
    private final GroupRepository groupRepository;
    private final SongRepository songRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;
    private final RepertoireMapper repertoireMapper;

    // RestTemplate para hacer request a FSRS
    private final RestTemplate restTemplate;

    @Value("${FSRS_SERVICE_URL}")
    private String FSRS_SERVICE_URL;

    @Transactional
    public RepertoireDto addSong(RepertoireRequestDto repertoireDto)
    {
        // Recuperar el grupo
        Group group = groupRepository.findById(repertoireDto.getGroupId())
            .orElseThrow(() -> new RuntimeException(
                "No se encontró el grupo con el id: " + repertoireDto.getGroupId()
            )
        );

        // Genero y artista con default value (pueden existir o no)
        Genre genre = null;
        Artist artist = null;

        // 1. Buscar un genero existente (Crear uno si no existe)
        // Si el genero es diff de null y si no es una cadena vacia
        if(repertoireDto.getGenre() != null && !repertoireDto.getGenre().trim().isEmpty())
        {
            Optional<Genre> optGenre = genreRepository.findByGroupIdAndGenreIgnoreCase(
              repertoireDto.getGroupId(), repertoireDto.getGenre().trim()
            );

            if(optGenre.isPresent())
            {
                genre = optGenre.get();
            }
            else
            {
                Genre newGenre = new Genre();
                newGenre.setGroup(group);
                newGenre.setGenre(repertoireDto.getGenre().trim());
                genre = genreRepository.save(newGenre);
            }
        }

        // 2. Buscar el artista existente (Crear uno si no existe)
        // Si el genero es diff de null y si no es una cadena vacia
        if(repertoireDto.getArtist() != null && !repertoireDto.getArtist().trim().isEmpty())
        {
            Optional<Artist> optArtist = artistRepository.findByGroupIdAndArtistIgnoreCase(
                    repertoireDto.getGroupId(), repertoireDto.getArtist().trim()
            );

            if(optArtist.isPresent())
            {
                artist = optArtist.get();
            }
            else
            {
                Artist newArtist = new Artist();
                newArtist.setGroup(group);
                newArtist.setArtist(repertoireDto.getArtist().trim());
                artist = artistRepository.save(newArtist);
            }
        }

        // 3. Crear la canción
        Song newSong = new Song();
        newSong.setGroup(group);
        newSong.setTitle(repertoireDto.getTitle().trim());
        newSong.setGenre(genre);
        newSong.setArtist(artist);
        newSong.setDuration(repertoireDto.getDuration());
        Song savedSong = songRepository.save(newSong);

        // 4. Crear la entrada en el repertorio
        Repertoire repertoire = new Repertoire();
        repertoire.setGroup(group);
        repertoire.setSong(savedSong);
        repertoire.setTone(repertoireDto.getTone());
        repertoire.setComment(repertoireDto.getComment());
        repertoire.setPerformance(repertoireDto.getPerformance());
        repertoire.setPopularity(repertoireDto.getPopularity());
        repertoire.setComplexity(repertoireDto.getComplexity());

        // Realizar la insercion en la base de datos
        Repertoire newRepertoire = repertoireRepository.save(repertoire);

        // Crear una nueva card (llamada a FSRS Service)
        Long repertoireId = newRepertoire.getId();
        if (repertoireId != null)
        {
            String fsrsServiceCardUrl = UriComponentsBuilder.fromUriString(FSRS_SERVICE_URL)
                .path("/init_card/" + repertoireId)
                .build()
                .toUriString();

            CardData cardData = null;
            try {
                cardData = restTemplate.getForObject(fsrsServiceCardUrl, CardData.class);
                System.out.println(cardData);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

            // Actualizar el campo card de la tabla
            newRepertoire.setCard(cardData);
            repertoireRepository.save(newRepertoire);
        }
        else
        {
            // handle repertoireId error
        }

        return repertoireMapper.toDto(newRepertoire);
    }

    @Transactional
    public RepertoireDto createRepertoire(RepertoireDto repertoireDto)
    {
        Repertoire repertoire = repertoireMapper.toEntity(repertoireDto);
        Repertoire savedRepertoire = repertoireRepository.save(repertoire);

        return repertoireMapper.toDto(savedRepertoire);
    }


    public List<RepertoireSongCardDto> getRepertoireByGroupId(Long groupId)
    {
        return new ArrayList<>(repertoireRepository.findRepertoireSongsCardsByGroupId(groupId));
    }


    public RepertoireDto getRepertoireById(Long id)
    {
        Optional<Repertoire> repertoire = repertoireRepository.findById(id);
        return repertoire.map(repertoireMapper::toDto).orElse(null);
    }


    @Transactional
    public RepertoireDto updateRepertoire(Long id, RepertoireDto repertoireDto)
    {
        Repertoire repertoire = repertoireRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de repertorio no encontrado, id: " + id));

        repertoireMapper.updateRepertoireFromDto(repertoireDto, repertoire);
        Repertoire updatedRepertoire = repertoireRepository.save(repertoire);

        return repertoireMapper.toDto(updatedRepertoire);
    }


    @Transactional
    public void deleteRepertoire(Long id)
    {
        if(!repertoireRepository.existsById(id))
        {
            throw new EntityNotFoundException("Registro de repertorio no encontrado, id: " + id);
        }
        repertoireRepository.deleteById(id);
    }

    private int parseDuration(String formattedDuration)
    {
        String[] parts = formattedDuration.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}
