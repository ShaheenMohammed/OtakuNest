package com.backend.backend.repository;

import com.backend.backend.domain.entity.ExternalMetadataCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ExternalMetadataCacheRepository extends JpaRepository<ExternalMetadataCache, UUID> {
}
