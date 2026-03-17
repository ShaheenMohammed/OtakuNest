package com.backend.backend.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
@Table(name = "episodes")
public class Episodes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id", nullable = false)
    private MediaItems mediaItem;

    @Column(name = "episode_number", nullable = false, precision = 6, scale = 2)
    private BigDecimal episodeNumber = BigDecimal.ONE;

    @Column(name = "title", length = 120)
    private String title;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "air_date")
    private OffsetDateTime airDate;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "is_filler")
    private Boolean isFiller = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "external_ids", columnDefinition = "JSONB")
    private String externalIds;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags", columnDefinition = "text[]")
    private List<String> tags;

    /* CONSTRUCTORS */
    public Episodes() {
    }

    public Episodes(MediaItems mediaItem, BigDecimal episodeNumber, String title) {
        this.mediaItem = mediaItem;
        this.episodeNumber = episodeNumber;
        this.title = title;
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

    public BigDecimal getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(BigDecimal episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public OffsetDateTime getAirDate() {
        return airDate;
    }

    public void setAirDate(OffsetDateTime airDate) {
        this.airDate = airDate;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Boolean getIsFiller() {
        return isFiller;
    }

    public void setIsFiller(Boolean isFiller) {
        this.isFiller = isFiller;
    }

    public String getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(String externalIds) {
        this.externalIds = externalIds;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
