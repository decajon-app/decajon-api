package com.decajon.decajon.services;

import com.decajon.decajon.dto.GenreDto;
import com.decajon.decajon.mappers.GenreMapper;
import com.decajon.decajon.models.Genre;
import com.decajon.decajon.repositories.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService
{
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;


    @Transactional
    public GenreDto createGenre(GenreDto genreDto)
    {
        Genre genre = genreMapper.toEntity(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.toDto(savedGenre);
    }


    public List<GenreDto> getGenresByGroupId(Long groupId)
    {
        return genreRepository.findByGroupId(groupId)
                .stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }


    public GenreDto getGenreById(Long id)
    {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado, id: " + id));
        return genreMapper.toDto(genre);
    }


    @Transactional
    public GenreDto updateGenre(Long id, GenreDto genreDto)
    {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado, id: " + id));

        genreMapper.updatedGenreFromDto(genreDto, genre);
        Genre updatedGenre = genreRepository.save(genre);

        return genreMapper.toDto(updatedGenre);
    }


    @Transactional
    public void deleteGenre(Long id)
    {
        if(!genreRepository.existsById(id))
        {
            throw new EntityNotFoundException("Genero no encontrado, id: " + id);
        }
        genreRepository.deleteById(id);
    }
}
