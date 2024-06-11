package com.qwammy.javaflupskilling_week_5.repositories;

import com.qwammy.javaflupskilling_week_5.entities.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, UUID> {
    CustomUser findByEmail(String email);
}
