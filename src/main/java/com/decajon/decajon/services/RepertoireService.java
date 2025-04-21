package com.decajon.decajon.services;

import com.decajon.decajon.dto.GenreDto;
import com.decajon.decajon.dto.RepertoireDto;
import com.decajon.decajon.dto.RepertoireRequestDto;
import com.decajon.decajon.mappers.RepertoireMapper;
import com.decajon.decajon.models.Artist;
import com.decajon.decajon.models.Genre;
import com.decajon.decajon.models.Repertoire;
import com.decajon.decajon.models.Song;
import com.decajon.decajon.repositories.ArtistRepository;
import com.decajon.decajon.repositories.GenreRepository;
import com.decajon.decajon.repositories.RepertoireRepository;
import com.decajon.decajon.repositories.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RepertoireService
{
    private final RepertoireRepository repertoireRepository;
    private final SongRepository songRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;
    private final RepertoireMapper repertoireMapper;


    @Transactional
    public RepertoireDto addSong(RepertoireRequestDto repertoireDto)
    {
        Long artistId = null;
        Long genreId = null;

        // 1. Buscar un genero existente (Crear uno si no existe)
        // Si el genero es diff de null y si no es una cadena vacia
        if(repertoireDto.getGenre() != null && !repertoireDto.getGenre().trim().isEmpty())
        {
            Optional<Genre> optGenre = genreRepository.findByGroupIdAndGenreIgnoreCase(
              repertoireDto.getGroupId(), repertoireDto.getGenre().trim()
            );

            if(optGenre.isPresent())
            {
                genreId = optGenre.get().getId();
            }
            else
            {
                Genre newGenre = new Genre();
                newGenre.setGroupId(repertoireDto.getGroupId());
                newGenre.setGenre(repertoireDto.getGenre().trim());
                Genre savedGenre = genreRepository.save(newGenre);
                genreId = savedGenre.getId();
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
                artistId = optArtist.get().getId();
            }
            else
            {
                Artist newArtist = new Artist();
                newArtist.setGroupId(repertoireDto.getGroupId());
                newArtist.setArtist(repertoireDto.getArtist().trim());
                Artist savedArtist = artistRepository.save(newArtist);
                artistId = savedArtist.getId();
            }
        }

        // 3. Crear la canción
        Song newSong = new Song();
        newSong.setGroupId(repertoireDto.getGroupId());
        newSong.setTitle(repertoireDto.getTitle().trim());
        newSong.setArtistId(artistId);
        newSong.setGenreId(genreId);
        newSong.setDuration(repertoireDto.getDuration());
        Song savedSong = songRepository.save(newSong);

        // 4. Crear la entrada en el repertorio
        Repertoire repertoire = new Repertoire();
        repertoire.setGroupId(repertoireDto.getGroupId());
        repertoire.setSongId(savedSong.getId());
        repertoire.setTone(repertoireDto.getTone());
        repertoire.setComment(repertoireDto.getComment());
        repertoire.setPerformance(repertoireDto.getPerformance());
        repertoire.setPopularity(repertoireDto.getPopularity());
        repertoire.setComplexity(repertoireDto.getComplexity());

        Repertoire newRepertoire = repertoireRepository.save(repertoire);
        return repertoireMapper.toDto(newRepertoire);
    }

    @Transactional
    public RepertoireDto createRepertoire(RepertoireDto repertoireDto)
    {
        Repertoire repertoire = repertoireMapper.toEntity(repertoireDto);
        Repertoire savedRepertoire = repertoireRepository.save(repertoire);

        return repertoireMapper.toDto(savedRepertoire);
    }


    public List<RepertoireDto> getRepertoireByGroupId(Long groupId)
    {
        return repertoireRepository.findBySong_GroupId(groupId)
                .stream()
                .map(repertoireMapper::toDto)
                .collect(Collectors.toList());
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
}
