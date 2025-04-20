package com.decajon.decajon.repositories;

import com.decajon.decajon.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>
{
    /**
     * Este metodo es para buscar que no exista un grupo con el mismo nombre.
     * @param name
     * @return
     */
    Optional<Group> findByName(String name);
}
