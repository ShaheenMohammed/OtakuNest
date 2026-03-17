package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "voice_actors")
public class VoiceActors {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "voice_actors_name", nullable = false, unique = true, length = 70)
    private String voiceActorsName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "external_ids", columnDefinition = "JSONB")
    private String externalIds;

    /* CONSTRUCTORS */
    public VoiceActors() {
    }

    public VoiceActors(String voiceActorsName, String externalIds) {
        this.voiceActorsName = voiceActorsName;
        this.externalIds = externalIds;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVoiceActorsName() {
        return voiceActorsName;
    }

    public void setVoiceActorsName(String voiceActorsName) {
        this.voiceActorsName = voiceActorsName;
    }

    public String getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(String externalIds) {
        this.externalIds = externalIds;
    }
}
