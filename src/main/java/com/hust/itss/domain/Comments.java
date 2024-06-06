package com.hust.itss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comments.
 */
@Entity
@Table(name = "comments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "anonymous_id")
    private UUID anonymousId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "urls", "comments", "subject" }, allowSetters = true)
    private Document document;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Comments id(Long id) {
        this.setId(id);
        return this;
    }

    public Comments createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public Comments updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public Comments anonymousId(UUID anonymousId) {
        this.setAnonymousId(anonymousId);
        return this;
    }

    public Comments document(Document document) {
        this.setDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comments)) {
            return false;
        }
        return getId() != null && getId().equals(((Comments) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comments{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", anonymousId='" + getAnonymousId() + "'" +
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

    public Document getDocument() {
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

    @JsonIgnoreProperties(value = { "urls", "comments", "subject" }, allowSetters = true)
    public void setDocument(Document document) {
        this.document = document;
    }
}
