package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.CastRoleType;

@Entity
@Table(name = "media_item_cast")
public class MediaItemCast {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id", nullable = false)
    private MediaItems mediaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voice_actor_id")
    private VoiceActors voiceActor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Characters character;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", columnDefinition = "cast_role_type")
    private CastRoleType roleType = CastRoleType.MAIN;

    /* CONSTRUCTORS */
    public MediaItemCast() {
    }

    public MediaItemCast(MediaItems mediaItem, VoiceActors voiceActor, Characters character, CastRoleType roleType) {
        this.mediaItem = mediaItem;
        this.voiceActor = voiceActor;
        this.character = character;
        this.roleType = roleType;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MediaItems getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItems mediaItem) {
        this.mediaItem = mediaItem;
    }

    public VoiceActors getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(VoiceActors voiceActor) {
        this.voiceActor = voiceActor;
    }

    public Characters getCharacter() {
        return character;
    }

    public void setCharacter(Characters character) {
        this.character = character;
    }

    public CastRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(CastRoleType roleType) {
        this.roleType = roleType;
    }
}
