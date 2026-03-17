package com.backend.backend.repository;

import com.backend.backend.domain.entity.Episodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EpisodesRepository extends JpaRepository<Episodes, UUID> {
}
