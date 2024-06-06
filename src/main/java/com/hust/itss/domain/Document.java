package com.hust.itss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image_url")
    private String imgUrl;

    @Column(name = "rating")
    private Double rating;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private Set<Url> urls = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private Set<Comments> comments = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents", "majors" }, allowSetters = true)
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public Document title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setUrls(Set<Url> urls) {
        if (this.urls != null) {
            this.urls.forEach(i -> i.setDocument(null));
        }
        if (urls != null) {
            urls.forEach(i -> i.setDocument(this));
        }
        this.urls = urls;
    }

    public Document urls(Set<Url> urls) {
        this.setUrls(urls);
        return this;
    }

    public void addUrls(Url url) {
        this.urls.add(url);
        url.setDocument(this);
    }

    public void removeUrls(Url url) {
        this.urls.remove(url);
        url.setDocument(null);
    }

    public void setComments(Set<Comments> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setDocument(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setDocument(this));
        }
        this.comments = comments;
    }

    public Document comments(Set<Comments> comments) {
        this.setComments(comments);
        return this;
    }

    public void addComments(Comments comments) {
        this.comments.add(comments);
        comments.setDocument(this);
    }

    public void removeComments(Comments comments) {
        this.comments.remove(comments);
        comments.setDocument(null);
    }

    public Document subject(Subject subject) {
        this.setSubject(subject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return getId() != null && getId().equals(((Document) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public Double getRating() {
        return this.rating;
    }

    public Set<Url> getUrls() {
        return this.urls;
    }

    public Set<Comments> getComments() {
        return this.comments;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @JsonIgnoreProperties(value = { "documents", "majors" }, allowSetters = true)
    @JsonIgnore
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
