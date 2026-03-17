package com.backend.backend.repository;

import com.backend.backend.domain.entity.Thumbnails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ThumbnailsRepository extends JpaRepository<Thumbnails, UUID> {
}
