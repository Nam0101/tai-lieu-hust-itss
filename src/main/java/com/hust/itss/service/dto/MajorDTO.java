package com.hust.itss.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class MajorDTO implements Serializable {

    private Long id;

    private String name;

    @JsonIgnore
    private Set<SubjectDTO> subjects = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MajorDTO)) {
            return false;
        }

        MajorDTO majorDTO = (MajorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, majorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MajorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subjects=" + getSubjects() +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Set<SubjectDTO> getSubjects() {
        return this.subjects;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public void setSubjects(Set<SubjectDTO> subjects) {
        this.subjects = subjects;
    }
}
