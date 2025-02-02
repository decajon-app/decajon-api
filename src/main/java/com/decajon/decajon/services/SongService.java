package com.decajon.decajon.services;

import com.decajon.decajon.dto.SongDto;
import com.decajon.decajon.mappers.SongMapper;
import com.decajon.decajon.models.Song;
import com.decajon.decajon.repositories.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService
{
    private final SongRepository songRepository;
    private final SongMapper songMapper;

    /**
     * Cancion que recibe un SongDto, lo transforma para guardarlo
     * en la base datos, y finalmente lo vuelve a transformar para regresarlo
     * nuevamente en su forma Dto.
     * @param songDto
     * @return SongDto
     */
    public SongDto createSong(SongDto songDto)
    {
        Song song = songMapper.toEntity(songDto);
        Song savedSong = songRepository.save(song);
        return songMapper.toDto(savedSong);
    }


    /**
     * Metodo que recupera todas las canciones de un grupo por su id.
     * @param groupId
     * @return List<SongDto>
     */
    public List<SongDto> getSongsByGroupId(Long groupId)
    {
        return songRepository.findByGroupId(groupId)
                .stream()
                .map(songMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Metodo para recuperar toda la información de una canción por su ID
     * @param id
     * @return SongDto
     */
    public SongDto getSongById(Long id)
    {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cancion solicitada no existe. ID: " + id));
        return songMapper.toDto(song);
    }


    /**
     *
     * @param id
     * @param songDto
     * @return SongDto
     */
    public SongDto updateSong(Long id, SongDto songDto)
    {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cancion solicitada no existe. ID: " + id));

        Song updatedSong = songRepository.save(song);
        return songMapper.toDto(updatedSong);
    }


    /**
     * Metodo para eliminar una cancion por su id.
     * @param id
     */
    public void deleteSong(Long id)
    {
        if(!songRepository.existsById(id))
        {
            throw new EntityNotFoundException("La cancion solicitada no existe. ID: " + id);
        }
        songRepository.deleteById(id);
    }

}
