package com.backend.backend.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
@Table(name = "chapters")
public class Chapters {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id", nullable = false)
    private MediaItems mediaItem;

    @Column(name = "chapter_number", nullable = false, precision = 6, scale = 2)
    private BigDecimal chapterNumber = BigDecimal.ONE;

    @Column(name = "volume_number")
    private Integer volumeNumber;

    @Column(name = "title", length = 120)
    private String title;

    @Column(name = "release_date")
    private OffsetDateTime releaseDate;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags", columnDefinition = "text[]")
    private List<String> tags;

    /* CONSTRUCTORS */
    public Chapters() {
    }

    public Chapters(MediaItems mediaItem, BigDecimal chapterNumber, String title) {
        this.mediaItem = mediaItem;
        this.chapterNumber = chapterNumber;
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

    public BigDecimal getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(BigDecimal chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Integer getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(Integer volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OffsetDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(OffsetDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
