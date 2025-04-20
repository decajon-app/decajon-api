package com.decajon.decajon.repositories;

import com.decajon.decajon.models.UserSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSongRepository extends JpaRepository<UserSong, Long>
{
    Optional<UserSong> findByUserIdAndGroupIdAndSongId(Long userId, Long groupId, Long songId);

    List<UserSong> findByGroupIdAndSongId(Long groupId, Long songId);

    List<UserSong> findByUserId(Long userId);

    List<UserSong> findByGroupId(Long groupId);

    void deleteByUserIdAndGroupIdAndSongId(Long userId, Long groupId, Long songId);
}
