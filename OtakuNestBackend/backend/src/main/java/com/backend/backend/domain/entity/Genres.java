package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "genre_name", nullable = false, unique = true, length = 120)
    private String genreName;

    /* CONSTRUCTORS */
    public Genres() {
    }

    public Genres(String genreName) {
        this.genreName = genreName;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
