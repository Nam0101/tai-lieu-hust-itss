package com.hust.itss.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.hust.itss.domain.Subject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubjectDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    @JsonIgnore
    private Set<MajorDTO> majors = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectDTO)) {
            return false;
        }

        SubjectDTO subjectDTO = (SubjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", majors=" + getMajors() +
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

    public Set<MajorDTO> getMajors() {
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

    @JsonIgnore
    public void setMajors(Set<MajorDTO> majors) {
        this.majors = majors;
    }
}
