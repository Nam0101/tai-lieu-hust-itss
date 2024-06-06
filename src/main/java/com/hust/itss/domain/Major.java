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
 * A Major.
 */
@Entity
@Table(name = "major")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_major__subjects",
        joinColumns = @JoinColumn(name = "major_id"),
        inverseJoinColumns = @JoinColumn(name = "subjects_id")
    )
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "majors" }, allowSetters = true)
    private Set<Subject> subjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Major id(Long id) {
        this.setId(id);
        return this;
    }

    public Major name(String name) {
        this.setName(name);
        return this;
    }

    public Major subjects(Set<Subject> subjects) {
        this.setSubjects(subjects);
        return this;
    }

    public void addSubjects(Subject subject) {
        this.subjects.add(subject);
    }

    public void removeSubjects(Subject subject) {
        this.subjects.remove(subject);
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Major)) {
            return false;
        }
        return getId() != null && getId().equals(((Major) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Major{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Set<Subject> getSubjects() {
        return this.subjects;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnoreProperties(value = { "documents", "majors" }, allowSetters = true)
    @JsonIgnore
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
