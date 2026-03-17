package com.backend.backend.domain.entity;

import java.util.UUID;
import java.math.BigDecimal;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.EntityType;

@Entity
@Table(name = "media_files")
public class MediaFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_media", nullable = false, columnDefinition = "entity_type")
    private EntityType typeOfMedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episodes episode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapters chapter;

    @Column(name = "file_path", nullable = false, unique = true, columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "file_hash", length = 255)
    private String fileHash;

    @Column(name = "media_file_checksum", length = 255)
    private String mediaFileChecksum;

    @Column(name = "video_codec", length = 50)
    private String videoCodec;

    @Column(name = "resolution_width")
    private Integer resolutionWidth;

    @Column(name = "resolution_height")
    private Integer resolutionHeight;

    @Column(name = "frame_rate", precision = 6, scale = 3)
    private BigDecimal frameRate;

    @Column(name = "subtitle_path", columnDefinition = "TEXT")
    private String subtitlePath;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    /* CONSTRUCTORS */
    public MediaFiles() {
    }

    public MediaFiles(EntityType typeOfMedia, String filePath, String fileName) {
        this.typeOfMedia = typeOfMedia;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EntityType getTypeOfMedia() {
        return typeOfMedia;
    }

    public void setTypeOfMedia(EntityType typeOfMedia) {
        this.typeOfMedia = typeOfMedia;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getMediaFileChecksum() {
        return mediaFileChecksum;
    }

    public void setMediaFileChecksum(String mediaFileChecksum) {
        this.mediaFileChecksum = mediaFileChecksum;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public void setResolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }

    public BigDecimal getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(BigDecimal frameRate) {
        this.frameRate = frameRate;
    }

    public String getSubtitlePath() {
        return subtitlePath;
    }

    public void setSubtitlePath(String subtitlePath) {
        this.subtitlePath = subtitlePath;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }
}
