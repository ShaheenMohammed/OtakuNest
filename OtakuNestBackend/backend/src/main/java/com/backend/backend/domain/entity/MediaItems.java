package com.backend.backend.domain.entity;

import com.backend.backend.domain.enums.MediaType;
import com.backend.backend.domain.enums.ReleaseStatus;
import com.backend.backend.domain.enums.SeasonType;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "media_items")
public class MediaItems {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_item_type", nullable = false, columnDefinition = "media_type")
    private MediaType mediaItemType;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_item_status", columnDefinition = "release_status")
    private ReleaseStatus mediaItemStatus;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "media_item_year")
    private Integer mediaItemYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "release_season", columnDefinition = "season_type")
    private SeasonType releaseSeason;

    @Column(name = "studio_name", length = 70)
    private String studioName;

    @Column(name = "artist", length = 70)
    private String artist;

    @Column(name = "groups", length = 70)
    private String groups;

    @Column(name = "cover_image_url", columnDefinition = "TEXT")
    private String coverImageUrl;

    @Column(name = "banner_image_url", columnDefinition = "TEXT")
    private String bannerImageUrl;

    @Column(name = "popularity_score", precision = 5, scale = 2)
    private BigDecimal popularityScore = BigDecimal.ZERO;

    @Column(name = "total_episodes_count")
    private Integer totalEpisodesCount = 0;

    @Column(name = "total_chapters_count")
    private Integer totalChaptersCount = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "external_ids", columnDefinition = "JSONB")
    private String externalIds;

    /*
     * Backend does not set or create a search_vector for the entity.
     * This is strictly done by database.
     */
    @Column(name = "search_vector", columnDefinition = "TSVECTOR", insertable = false, updatable = false)
    private String searchVector;

    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episodes> episodes;
    
    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapters> chapters;

    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaItemGenres> genres;

    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaItemTags> tags;

    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaItemCast> cast;

    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Thumbnails> thumbnails;

    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProgress> userProgress;
    
    /* CONSTRUCTORS */

    public MediaItems() {}

    public MediaItems(
            UUID id,
            MediaType mediaItemType,
            String title,
            String synopsis,
            ReleaseStatus mediaItemStatus,
            LocalDate releaseDate,
            Integer mediaItemYear,
            SeasonType releaseSeason,
            String studioName,
            String artist,
            String groups,
            String coverImageUrl,
            String bannerImageUrl,
            BigDecimal popularityScore,
            Integer totalEpisodesCount,
            Integer totalChaptersCount,
            String externalIds,
            String searchVector) {
        this.id = id;
        this.mediaItemType = mediaItemType;
        this.title = title;
        this.synopsis = synopsis;
        this.mediaItemStatus = mediaItemStatus;
        this.releaseDate = releaseDate;
        this.mediaItemYear = mediaItemYear;
        this.releaseSeason = releaseSeason;
        this.studioName = studioName;
        this.artist = artist;
        this.groups = groups;
        this.coverImageUrl = coverImageUrl;
        this.bannerImageUrl = bannerImageUrl;
        this.popularityScore = popularityScore;
        this.totalEpisodesCount = totalEpisodesCount;
        this.totalChaptersCount = totalChaptersCount;
        this.externalIds = externalIds;
        this.searchVector = searchVector;
    }

    /* GETTERS & SETTERS */

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MediaType getMediaItemType() {
        return mediaItemType;
    }

    public void setMediaItemType(MediaType mediaItemType) {
        this.mediaItemType = mediaItemType;
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

    public ReleaseStatus getMediaItemStatus() {
        return mediaItemStatus;
    }

    public void setMediaItemStatus(ReleaseStatus mediaItemStatus) {
        this.mediaItemStatus = mediaItemStatus;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getMediaItemYear() {
        return mediaItemYear;
    }

    public void setMediaItemYear(Integer mediaItemYear) {
        this.mediaItemYear = mediaItemYear;
    }

    public SeasonType getReleaseSeason() {
        return releaseSeason;
    }

    public void setReleaseSeason(SeasonType releaseSeason) {
        this.releaseSeason = releaseSeason;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public BigDecimal getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(BigDecimal popularityScore) {
        this.popularityScore = popularityScore;
    }

    public Integer getTotalEpisodesCount() {
        return totalEpisodesCount;
    }

    public void setTotalEpisodesCount(Integer totalEpisodesCount) {
        this.totalEpisodesCount = totalEpisodesCount;
    }

    public Integer getTotalChaptersCount() {
        return totalChaptersCount;
    }

    public void setTotalChaptersCount(Integer totalChaptersCount) {
        this.totalChaptersCount = totalChaptersCount;
    }

    public String getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(String externalIds) {
        this.externalIds = externalIds;
    }

    public String getSearchVector() {
        return searchVector;
    }

    public List<Episodes> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episodes> episodes) {
        this.episodes = episodes;
    }

    public List<Chapters> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapters> chapters) {
        this.chapters = chapters;
    }

    public List<MediaItemGenres> getGenres() {
        return genres;
    }

    public void setGenres(List<MediaItemGenres> genres) {
        this.genres = genres;
    }

    public List<MediaItemTags> getTags() {
        return tags;
    }

    public void setTags(List<MediaItemTags> tags) {
        this.tags = tags;
    }

    public List<MediaItemCast> getCast() {
        return cast;
    }

    public void setCast(List<MediaItemCast> cast) {
        this.cast = cast;
    }

    public List<Thumbnails> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Thumbnails> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<UserProgress> getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(List<UserProgress> userProgress) {
        this.userProgress = userProgress;
    }
}
