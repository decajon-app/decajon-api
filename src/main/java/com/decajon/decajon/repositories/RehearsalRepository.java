package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Rehearsal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RehearsalRepository extends JpaRepository<Rehearsal, Long>
{
    List<Rehearsal> findByGroupId(Long groupId);
}
