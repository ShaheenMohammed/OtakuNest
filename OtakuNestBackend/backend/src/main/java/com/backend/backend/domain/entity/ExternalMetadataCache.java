package com.backend.backend.domain.entity;

import java.util.UUID;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.SourceApiType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "external_metadata_cache", uniqueConstraints = {@UniqueConstraint(columnNames = {"external_id", "source_api"})})
public class ExternalMetadataCache {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "external_id", nullable = false, length = 255)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_api", nullable = false, columnDefinition = "source_api_type")
    private SourceApiType sourceApi;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "raw_json", columnDefinition = "JSONB")
    private String rawJson;

    /* CONSTRUCTORS */
    public ExternalMetadataCache() {
    }

    public ExternalMetadataCache(String externalId, SourceApiType sourceApi, String rawJson) {
        this.externalId = externalId;
        this.sourceApi = sourceApi;
        this.rawJson = rawJson;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public SourceApiType getSourceApi() {
        return sourceApi;
    }

    public void setSourceApi(SourceApiType sourceApi) {
        this.sourceApi = sourceApi;
    }

    public String getRawJson() {
        return rawJson;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }
}
