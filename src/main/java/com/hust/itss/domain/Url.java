package com.hust.itss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Url.
 */
@Entity
@Table(name = "url")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "drive_url")
    private String driveUrl;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "urls", "comments", "subject" }, allowSetters = true)
    private Document document;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Url id(Long id) {
        this.setId(id);
        return this;
    }

    public Url driveUrl(String driveUrl) {
        this.setDriveUrl(driveUrl);
        return this;
    }

    public Url type(String type) {
        this.setType(type);
        return this;
    }

    public Url document(Document document) {
        this.setDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Url)) {
            return false;
        }
        return getId() != null && getId().equals(((Url) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Url{" +
            "id=" + getId() +
            ", driveUrl='" + getDriveUrl() + "'" +
            ", type='" + getType() + "'" +
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

    public Document getDocument() {
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

    @JsonIgnoreProperties(value = { "urls", "comments", "subject" }, allowSetters = true)
    public void setDocument(Document document) {
        this.document = document;
    }
}
