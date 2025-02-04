package com.decajon.decajon.services;

import com.decajon.decajon.dto.RepertoireDto;
import com.decajon.decajon.mappers.RepertoireMapper;
import com.decajon.decajon.models.Repertoire;
import com.decajon.decajon.repositories.RepertoireRepository;
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
    private final RepertoireMapper repertoireMapper;


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
