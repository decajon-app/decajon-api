package com.decajon.decajon.services;

import com.decajon.decajon.dto.ArtistDto;
import com.decajon.decajon.mappers.ArtistMapper;
import com.decajon.decajon.models.Artist;
import com.decajon.decajon.repositories.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService
{
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;


    @Transactional
    public ArtistDto createArtist(ArtistDto artistDto)
    {
        Artist artist = artistMapper.toEntity(artistDto);
        Artist savedArtist = artistRepository.save(artist);

        return artistMapper.toDto(savedArtist);
    }


    public List<ArtistDto> getArtistsByGroupId(Long id)
    {
        return artistRepository.findByGroupId(id)
                .stream()
                .map(artistMapper::toDto)
                .collect(Collectors.toList());
    }


    public ArtistDto getArtistById(Long id)
    {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artista no encontrado, id: " + id));
        return artistMapper.toDto(artist);
    }


    @Transactional
    public ArtistDto updateArtist(Long id, ArtistDto artistDto)
    {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado, id: " + id));

        artistMapper.updateArtistFromDto(artistDto, artist);
        Artist updatedArtist = artistRepository.save(artist);

        return artistMapper.toDto(updatedArtist);
    }


    @Transactional
    public void deleteArtist(Long id)
    {
        if(!artistRepository.existsById(id))
        {
            throw new EntityNotFoundException("Artista no encontrado, id: " + id);
        }
        artistRepository.deleteById(id);
    }
}
