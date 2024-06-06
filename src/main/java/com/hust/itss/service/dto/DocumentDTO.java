package com.hust.itss.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentDTO implements Serializable {

    private Long id;

    private String title;

    @JsonIgnore
    private SubjectDTO subject;

    private Double rating;

    private String imgUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentDTO documentDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", subject=" + getSubject() +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public SubjectDTO getSubject() {
        return this.subject;
    }

    public Double getRating() {
        return this.rating;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
