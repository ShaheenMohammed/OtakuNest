package com.backend.backend.domain.entity;

import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.persistence.*;
import com.backend.backend.domain.enums.ScannerJobStatus;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "scanner_jobs")
public class ScannerJobs {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_status", columnDefinition = "scanner_job_status")
    private ScannerJobStatus jobStatus = ScannerJobStatus.PENDING;

    @UpdateTimestamp
    @Column(name = "heartbeat")
    private OffsetDateTime heartbeat;

    @Column(name = "error_log", columnDefinition = "TEXT")
    private String errorLog;

    /* CONSTRUCTORS */
    public ScannerJobs() {
    }

    public ScannerJobs(ScannerJobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    /* GETTERS & SETTERS */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ScannerJobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(ScannerJobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public OffsetDateTime getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(OffsetDateTime heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}
