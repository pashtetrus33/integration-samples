package com.example.service.integration_app.repository;

import com.example.service.integration_app.entity.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DatabaseEntityRepository extends JpaRepository<DatabaseEntity, UUID> {
}
