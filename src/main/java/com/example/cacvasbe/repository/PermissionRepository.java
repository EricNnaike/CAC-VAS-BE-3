package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.Permission;
import com.example.cacvasbe.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findAllByRole(Role role);
}
