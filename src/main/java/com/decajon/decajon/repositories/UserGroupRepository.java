package com.decajon.decajon.repositories;

import com.decajon.decajon.models.UserGroup;
import com.decajon.decajon.models.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId>
{
    List<UserGroup> findByIdUserId(Long userId);
    List<UserGroup> findByIdGroupId(Long groupId);
}
