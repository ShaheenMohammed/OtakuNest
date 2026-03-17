package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "media_item_genres")
public class MediaItemGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id", nullable = false)
    private MediaItems mediaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genres genre;

    /* CONSTRUCTORS */
    public MediaItemGenres() {
    }

    public MediaItemGenres(MediaItems mediaItem, Genres genre) {
        this.mediaItem = mediaItem;
        this.genre = genre;
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

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }
}
