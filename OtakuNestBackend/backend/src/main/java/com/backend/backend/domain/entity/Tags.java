package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tag_name", nullable = false, unique = true, length = 100)
    private String tagName;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    /* CONSTRUCTORS */
    public Tags() {
    }

    public Tags(String tagName, String synopsis) {
        this.tagName = tagName;
        this.synopsis = synopsis;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
