package com.decajon.decajon.services;

import com.decajon.decajon.dto.RehearsalDto;
import com.decajon.decajon.mappers.RehearsalMapper;
import com.decajon.decajon.models.Rehearsal;
import com.decajon.decajon.models.Song;
import com.decajon.decajon.repositories.RehearsalRepository;
import com.decajon.decajon.repositories.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RehearsalService
{
    private final RehearsalRepository rehearsalRepository;
    private final SongRepository songRepository;
    private final RehearsalMapper rehearsalMapper;

    public RehearsalDto getRehearsalById(Long id)
    {
        Rehearsal rehearsal = rehearsalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rehearsal not found with id: " + id));
        return rehearsalMapper.toDto(rehearsal);
    }

    @Transactional
    public RehearsalDto createRehearsal(RehearsalDto rehearsalDto)
    {
        Rehearsal rehearsal = rehearsalMapper.toEntity(rehearsalDto);
        if(rehearsalDto.getSongIds() != null && !rehearsalDto.getSongIds().isEmpty())
        {
            List<Song> songs = songRepository.findAllById(rehearsalDto.getSongIds());
            rehearsal.setSongs(songs);
        }
        Rehearsal savedRehearsal = rehearsalRepository.save(rehearsal);
        return rehearsalMapper.toDto(savedRehearsal);
    }

    public void deleteRehearsal(Long id)
    {
        rehearsalRepository.deleteById(id);
    }
}
