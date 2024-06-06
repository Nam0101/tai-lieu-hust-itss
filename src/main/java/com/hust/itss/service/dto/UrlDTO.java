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

    public Long getId() {
        return this.id;
    }

    public String getDriveUrl() {
        return this.driveUrl;
    }

    public String getType() {
        return this.type;
    }

    public DocumentDTO getDocument() {
        return this.document;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDriveUrl(String driveUrl) {
        this.driveUrl = driveUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }
}
