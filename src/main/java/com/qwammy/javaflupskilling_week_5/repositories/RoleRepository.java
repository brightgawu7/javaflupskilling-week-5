package com.qwammy.javaflupskilling_week_5.repositories;

import com.qwammy.javaflupskilling_week_5.entities.Role;
import com.qwammy.javaflupskilling_week_5.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRole(UserRoles role);
}
