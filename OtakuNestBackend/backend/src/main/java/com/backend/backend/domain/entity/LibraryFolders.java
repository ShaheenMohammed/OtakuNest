package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.MediaType;

@Entity
@Table(name = "library_folders")
public class LibraryFolders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "folder_path", nullable = false, unique = true, columnDefinition = "TEXT")
    private String folderPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_folder_type", nullable = false, columnDefinition = "media_type")
    private MediaType mediaFolderType;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    /* CONSTRUCTORS */
    public LibraryFolders() {
    }

    public LibraryFolders(String folderPath, MediaType mediaFolderType, Boolean isEnabled) {
        this.folderPath = folderPath;
        this.mediaFolderType = mediaFolderType;
        this.isEnabled = isEnabled;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public MediaType getMediaFolderType() {
        return mediaFolderType;
    }

    public void setMediaFolderType(MediaType mediaFolderType) {
        this.mediaFolderType = mediaFolderType;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
