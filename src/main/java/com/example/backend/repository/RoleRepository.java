package com.example.backend.repository;
import java.util.Optional;

import com.example.backend.models.role.ERole;
import com.example.backend.models.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}