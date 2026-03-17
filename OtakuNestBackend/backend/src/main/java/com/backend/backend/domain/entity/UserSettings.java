package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "theme", length = 20)
    private String theme = "dark";

    @Column(name = "auto_play")
    private Boolean autoPlay = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "external_api_keys", columnDefinition = "JSONB")
    private String externalApiKeys;

    /* CONSTRUCTORS */
    public UserSettings() {
    }

    public UserSettings(String theme, Boolean autoPlay, String externalApiKeys) {
        this.theme = theme;
        this.autoPlay = autoPlay;
        this.externalApiKeys = externalApiKeys;
    }

    /* GETTER & SETTERS */
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(Boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public String getExternalApiKeys() {
        return externalApiKeys;
    }

    public void setExternalApiKeys(String externalApiKeys) {
        this.externalApiKeys = externalApiKeys;
    }
}
