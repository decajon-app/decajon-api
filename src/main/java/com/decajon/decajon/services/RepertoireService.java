package com.decajon.decajon.services;

import com.decajon.decajon.dto.*;
import com.decajon.decajon.mappers.RepertoireMapper;
import com.decajon.decajon.models.*;
import com.decajon.decajon.repositories.*;
import com.decajon.decajon.utils.DateTimeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final ObjectMapper objectMapper;

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

    public Optional<RepertoireSongDto> getSongDetailsByRepertoireId(Long repertoireId)
    {
        return repertoireRepository.findSongDetailsByRepertoireId(repertoireId);
    }

    @Transactional
    public RepertoireDto createRepertoire(RepertoireDto repertoireDto)
    {
        Repertoire repertoire = repertoireMapper.toEntity(repertoireDto);
        Repertoire savedRepertoire = repertoireRepository.save(repertoire);

        return repertoireMapper.toDto(savedRepertoire);
    }

    @Transactional
    public void reviewCard(RepertoireReviewSongDto reviewSongDto)
    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Verificar si existe una entrada en la tabla con el id proporcionado
        Optional<Repertoire> optRepertoire = repertoireRepository.findById(reviewSongDto.repertoireId());
        if(optRepertoire.isEmpty()) {
            throw new EntityNotFoundException("No existe una entrada con ese ID.");
        }

        // 1. Recuperar la col 'card' de la tabla Repertoires
        String jsonCard = repertoireRepository.findCardJsonById(reviewSongDto.repertoireId());
        if (jsonCard == null) {
            throw new EntityNotFoundException("El id proporcionado no cuenta con una card inicializada");
        }

        // 2. Construir el request para mandar a review_card de FSRS
        String fsrsServiceReviewCardUrl = UriComponentsBuilder.fromUriString(FSRS_SERVICE_URL)
                .path("/review_card/" + reviewSongDto.repertoireId())
                .build()
                .toUriString();
        CardData updatedCardData = null;
        try
        {
            CardData cardData = objectMapper.readValue(jsonCard, CardData.class);
            if(cardData.getStep() == null) {
                cardData.setStep(0);
            }
            ReviewCardRequestDto reviewCardRequestDto = new ReviewCardRequestDto(
                    reviewSongDto.rating(),
                    OffsetDateTime.now(ZoneOffset.UTC),
                    cardData
            );
            System.out.println("Pre-review: " + reviewCardRequestDto.card_data());
            updatedCardData = restTemplate.postForObject(fsrsServiceReviewCardUrl, reviewCardRequestDto, CardData.class);
            System.out.println("Post-review: " + updatedCardData);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error en el servidor FSRS, no se pudo procesar la card.");
        }

        Repertoire repertoireToUpdate = optRepertoire.get();
        repertoireToUpdate.setCard(updatedCardData);
        repertoireRepository.save(repertoireToUpdate);
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

    public List<SuggestionCardDto> getSuggestionsByUserId(Long userId)
    {
        return repertoireRepository.findSuggestionsByUserId(userId).stream()
                .map(p -> new SuggestionCardDto(
                        p.getRepertoireId(),
                        p.getTitle(),
                        p.getArtist(),
                        p.getGroup(),
                        p.getPerformance(),
                        DateTimeConverter.convertToGuadalajaraTime(p.getDueDate())
                )).limit(5).toList();
    }

    public List<SuggestionCardDto> getSuggestionsByGroupId(Long groupId)
    {
        return repertoireRepository.findSuggestionsByGroupId(groupId).stream()
                .map(p -> new SuggestionCardDto(
                        p.getRepertoireId(),
                        p.getTitle(),
                        p.getArtist(),
                        p.getGroup(),
                        p.getPerformance(),
                        DateTimeConverter.convertToGuadalajaraTime(p.getDueDate())
                )).limit(5).toList();
    }
}
