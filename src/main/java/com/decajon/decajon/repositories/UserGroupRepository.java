package com.decajon.decajon.repositories;

import com.decajon.decajon.dto.GroupMemberDto;
import com.decajon.decajon.models.UserGroup;
import com.decajon.decajon.models.UserGroupId;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId>
{
    List<UserGroup> findByIdUserId(Long userId);
    List<UserGroup> findByIdGroupId(Long groupId);

    @Query("SELECT COUNT(ug) FROM UserGroup ug WHERE ug.id.groupId = :groupId")
    long countByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT new com.decajon.decajon.dto.GroupMemberDto(ug.id.userId, u.firstName, u.lastName) " +
            "FROM UserGroup ug JOIN User u ON ug.id.userId = u.id WHERE ug.id.groupId = :groupId")
    List<GroupMemberDto> findGroupMembers(@Param("groupId") Long groupId);

    @Query("SELECT COUNT(ug) > 0 FROM UserGroup ug WHERE ug.id.groupId = :groupId AND ug.id.userId = :userId")
    boolean isMemberOfGroup(Long groupId, Long userId);
}
