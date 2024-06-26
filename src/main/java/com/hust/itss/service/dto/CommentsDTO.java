package com.hust.itss.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.hust.itss.domain.Comments} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentsDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private UUID anonymousId;

    private DocumentDTO document;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentsDTO commentsDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentsDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", anonymousId='" + getAnonymousId() + "'" +
            ", document=" + getDocument() +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public UUID getAnonymousId() {
        return this.anonymousId;
    }

    public DocumentDTO getDocument() {
        return this.document;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAnonymousId(UUID anonymousId) {
        this.anonymousId = anonymousId;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }
}
