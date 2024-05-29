package com.hust.itss.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hust.itss.domain.Url} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UrlDTO implements Serializable {

    private Long id;

    private String driveUrl;

    private String type;

    private DocumentDTO document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriveUrl() {
        return driveUrl;
    }

    public void setDriveUrl(String driveUrl) {
        this.driveUrl = driveUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UrlDTO)) {
            return false;
        }

        UrlDTO urlDTO = (UrlDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, urlDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UrlDTO{" +
            "id=" + getId() +
            ", driveUrl='" + getDriveUrl() + "'" +
            ", type='" + getType() + "'" +
            ", document=" + getDocument() +
            "}";
    }
}
