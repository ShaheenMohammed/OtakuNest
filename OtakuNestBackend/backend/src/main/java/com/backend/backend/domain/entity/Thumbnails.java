package com.backend.backend.domain.entity;

import java.util.UUID;
import java.math.BigDecimal;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.ThumbnailUsage;

@Entity
@Table(name = "thumbnails")
public class Thumbnails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id")
    private MediaItems mediaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episodes episode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapters chapter;

    @Enumerated(EnumType.STRING)
    @Column(name = "usage_type", nullable = false, columnDefinition = "thumbnail_usage")
    private ThumbnailUsage usageType;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "timestamp_seconds", precision = 6, scale = 2)
    private BigDecimal timestampSeconds;

    @Column(name = "image_path", nullable = false, columnDefinition = "TEXT")
    private String imagePath;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "is_generated")
    private Boolean isGenerated = false;

    /* CONSTRUCTORS */
    public Thumbnails() {
    }

    public Thumbnails(ThumbnailUsage usageType, String imagePath) {
        this.usageType = usageType;
        this.imagePath = imagePath;
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

    public Episodes getEpisode() {
        return episode;
    }

    public void setEpisode(Episodes episode) {
        this.episode = episode;
    }

    public Chapters getChapter() {
        return chapter;
    }

    public void setChapter(Chapters chapter) {
        this.chapter = chapter;
    }

    public ThumbnailUsage getUsageType() {
        return usageType;
    }

    public void setUsageType(ThumbnailUsage usageType) {
        this.usageType = usageType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BigDecimal getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(BigDecimal timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getIsGenerated() {
        return isGenerated;
    }

    public void setIsGenerated(Boolean isGenerated) {
        this.isGenerated = isGenerated;
    }
}
