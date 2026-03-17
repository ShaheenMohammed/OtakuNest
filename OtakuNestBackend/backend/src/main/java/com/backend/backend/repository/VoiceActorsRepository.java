package com.backend.backend.repository;

import com.backend.backend.domain.entity.VoiceActors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VoiceActorsRepository extends JpaRepository<VoiceActors, UUID> {
}
