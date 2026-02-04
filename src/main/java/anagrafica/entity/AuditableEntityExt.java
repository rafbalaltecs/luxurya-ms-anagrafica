package anagrafica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntityExt {
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "LastModifiedDate")
    private LocalDateTime updatedAt;
    @Column(name = "CreatedBy")
    private String createdBy;
    @Column(name = "LastModifiedBy")
    private String updatedBy;
    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        this.createdAt = localDateTime;
        this.updatedAt = localDateTime;
        this.isDeleted = Boolean.FALSE;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
