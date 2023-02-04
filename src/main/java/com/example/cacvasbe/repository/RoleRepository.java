package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Optional<Role> findByNameIgnoreCase(String name);
    Optional<Role> findByName(RoleType roleType);



}
