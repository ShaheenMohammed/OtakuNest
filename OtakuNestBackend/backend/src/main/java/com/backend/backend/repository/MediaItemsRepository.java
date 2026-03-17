package com.backend.backend.repository;

import com.backend.backend.domain.entity.MediaItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MediaItemsRepository extends JpaRepository<MediaItems, UUID> {
}
