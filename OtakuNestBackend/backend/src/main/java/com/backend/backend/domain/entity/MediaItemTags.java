package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "media_item_tags")
public class MediaItemTags {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id", nullable = false)
    private MediaItems mediaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tags tag;

    @Column(name = "rank")
    private Integer rank = 0;

    /* CONSTRUCTORS */
    public MediaItemTags() {
    }

    public MediaItemTags(MediaItems mediaItem, Tags tag, Integer rank) {
        this.mediaItem = mediaItem;
        this.tag = tag;
        this.rank = rank;
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

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
