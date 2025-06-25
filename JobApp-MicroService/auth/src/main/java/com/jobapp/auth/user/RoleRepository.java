package com.jobapp.auth.user;

import com.jobapp.auth.models.AppRole;
import com.jobapp.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}

