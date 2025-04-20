package com.decajon.decajon.services;

import com.decajon.decajon.dto.UserSongDto;
import com.decajon.decajon.mappers.UserSongMapper;
import com.decajon.decajon.models.UserSong;
import com.decajon.decajon.repositories.UserSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSongService
{
    private final UserSongRepository userSongRepository;

    private final UserSongMapper userSongMapper;

    public UserSongDto save(UserSongDto userSongDto) {
        UserSong userSong = userSongMapper.toEntity(userSongDto);
        userSong = userSongRepository.save(userSong);
        return userSongMapper.toDto(userSong);
    }

    public Optional<UserSongDto> findById(Long id) {
        return userSongRepository.findById(id).map(userSongMapper::toDto);
    }

    public Optional<UserSongDto> findByUserGroupSong(Long userId, Long groupId, Long songId) {
        return userSongRepository.findByUserIdAndGroupIdAndSongId(userId, groupId, songId)
                .map(userSongMapper::toDto);
    }

    public List<UserSongDto> findByGroupAndSong(Long groupId, Long songId) {
        return userSongRepository.findByGroupIdAndSongId(groupId, songId)
                .stream()
                .map(userSongMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserSongDto> findByUser(Long userId) {
        return userSongRepository.findByUserId(userId)
                .stream()
                .map(userSongMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserSongDto> findByGroup(Long groupId) {
        return userSongRepository.findByGroupId(groupId)
                .stream()
                .map(userSongMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long userId, Long groupId, Long songId) {
        userSongRepository.deleteByUserIdAndGroupIdAndSongId(userId, groupId, songId);
    }
}
