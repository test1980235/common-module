package com.smartlogis.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    protected void createdBy(final String username) {
        this.createdBy = username;
    }

    protected void updatedBy(final String username) {
        this.updatedBy = username;
    }

    protected void deletedBy(final String username) {
        this.deletedBy = username;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        deletedBy(Objects.isNull(authentication) ? "SYSTEM" : authentication.getName());
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AbstractEntity entity)) { return false; }
        return Objects.equals(createdAt, entity.createdAt)
                && Objects.equals(createdBy, entity.createdBy)
                && Objects.equals(updatedAt, entity.updatedAt)
                && Objects.equals(updatedBy, entity.updatedBy)
                && Objects.equals(deletedAt, entity.deletedAt)
                && Objects.equals(deletedBy, entity.deletedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, createdBy, updatedAt, updatedBy, deletedAt, deletedBy);
    }
}