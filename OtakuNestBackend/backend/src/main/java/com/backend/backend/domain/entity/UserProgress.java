package com.backend.backend.domain.entity;

import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.WatchingStatus;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user_progress", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "media_item_id"})})
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id", nullable = false)
    private MediaItems mediaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_episode_id")
    private Episodes lastEpisode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_chapter_id")
    private Chapters lastChapter;

    @Column(name = "watched_seconds")
    private Integer watchedSeconds = 0;

    @Column(name = "user_rating")
    private Integer userRating;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_item_status", nullable = false, columnDefinition = "watching_status")
    private WatchingStatus mediaItemStatus;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    /* CONSTRUCTORS */
    public UserProgress() {
    }

    public UserProgress(Users user, MediaItems mediaItem, WatchingStatus mediaItemStatus) {
        this.user = user;
        this.mediaItem = mediaItem;
        this.mediaItemStatus = mediaItemStatus;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public MediaItems getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItems mediaItem) {
        this.mediaItem = mediaItem;
    }

    public Episodes getLastEpisode() {
        return lastEpisode;
    }

    public void setLastEpisode(Episodes lastEpisode) {
        this.lastEpisode = lastEpisode;
    }

    public Chapters getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(Chapters lastChapter) {
        this.lastChapter = lastChapter;
    }

    public Integer getWatchedSeconds() {
        return watchedSeconds;
    }

    public void setWatchedSeconds(Integer watchedSeconds) {
        this.watchedSeconds = watchedSeconds;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public WatchingStatus getMediaItemStatus() {
        return mediaItemStatus;
    }

    public void setMediaItemStatus(WatchingStatus mediaItemStatus) {
        this.mediaItemStatus = mediaItemStatus;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
