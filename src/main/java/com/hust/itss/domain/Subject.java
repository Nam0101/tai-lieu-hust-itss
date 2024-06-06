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
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "urls", "comments", "subject" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subjects" }, allowSetters = true)
    private Set<Major> majors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Subject id(Long id) {
        this.setId(id);
        return this;
    }

    public Subject name(String name) {
        this.setName(name);
        return this;
    }

    public Subject code(String code) {
        this.setCode(code);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setSubject(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setSubject(this));
        }
        this.documents = documents;
    }

    public Subject documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public Subject addDocuments(Document document) {
        this.documents.add(document);
        document.setSubject(this);
        return this;
    }

    public Subject removeDocuments(Document document) {
        this.documents.remove(document);
        document.setSubject(null);
        return this;
    }

    public void setMajors(Set<Major> majors) {
        if (this.majors != null) {
            this.majors.forEach(i -> i.removeSubjects(this));
        }
        if (majors != null) {
            majors.forEach(i -> i.addSubjects(this));
        }
        this.majors = majors;
    }

    public Subject majors(Set<Major> majors) {
        this.setMajors(majors);
        return this;
    }

    public Subject addMajors(Major major) {
        this.majors.add(major);
        major.getSubjects().add(this);
        return this;
    }

    public Subject removeMajors(Major major) {
        this.majors.remove(major);
        major.getSubjects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        return getId() != null && getId().equals(((Subject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public Set<Major> getMajors() {
        return this.majors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
