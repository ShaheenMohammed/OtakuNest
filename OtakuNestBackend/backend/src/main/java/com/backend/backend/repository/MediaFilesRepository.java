package com.backend.backend.repository;

import com.backend.backend.domain.entity.MediaFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MediaFilesRepository extends JpaRepository<MediaFiles, UUID> {
}
