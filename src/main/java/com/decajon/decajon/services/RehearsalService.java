package com.decajon.decajon.services;

import com.decajon.decajon.dto.RehearsalDto;
import com.decajon.decajon.dto.UpdateRehearsalDto;
import com.decajon.decajon.mappers.RehearsalMapper;
import com.decajon.decajon.models.Group;
import com.decajon.decajon.models.Rehearsal;
import com.decajon.decajon.models.Song;
import com.decajon.decajon.repositories.GroupRepository;
import com.decajon.decajon.repositories.RehearsalRepository;
import com.decajon.decajon.repositories.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RehearsalService
{
    private final RehearsalRepository rehearsalRepository;
    private final GroupRepository groupRepository;
    private final SongRepository songRepository;
    private final RehearsalMapper rehearsalMapper;

    public List<RehearsalDto> listByGroup(Long groupId)
    {
        if (!groupRepository.existsById(groupId))
        {
            throw new EntityNotFoundException("Grupo no encontrado para recuperar ensayos.");
        }

        List<Rehearsal> rehearsals = rehearsalRepository.findByGroupId(groupId);
        return rehearsals.stream()
                .map(rehearsalMapper::toDto)
                .toList();
    }

    @Transactional
    public RehearsalDto create(Long groupId, RehearsalDto rehearsalDto)
    {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado"));

        Rehearsal rehearsal = new Rehearsal();
        rehearsal.setGroup(group);
        rehearsal.setScheduledAt(rehearsalDto.scheduledAt().atStartOfDay());

        if (rehearsalDto.songIds() != null && !rehearsalDto.songIds().isEmpty()) {
            Set<Song> songs = getSongsFromIds(rehearsalDto.songIds());
            rehearsal.setSongs(songs);
        }

        return rehearsalMapper.toDto(rehearsalRepository.save(rehearsal));
    }

    public RehearsalDto getById(Long id)
    {
        return null;
    }

    public RehearsalDto updateById(Long id, UpdateRehearsalDto rehearsalDto)
    {
        return null;
    }

    public void deleteById(Long id)
    {

    }

    public RehearsalDto addSongsById(Long id, Set<Long> songIds)
    {
        return null;
    }

    public RehearsalDto removeSongsById(Long id, Set<Long> songIds)
    {
        return null;
    }
    private Set<Song> getSongsFromIds(Collection<Long> songIds) {
        List<Song> songs = songRepository.findAllById(songIds);
        if (songs.size() != songIds.size()) {
            throw new EntityNotFoundException("Una o más canciones no fueron encontradas");
        }
        return new HashSet<>(songs);
    }
}
