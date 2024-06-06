package com.hust.itss.service.mapper;

import com.hust.itss.domain.Major;
import com.hust.itss.domain.Subject;
import com.hust.itss.service.dto.MajorDTO;
import com.hust.itss.service.dto.SubjectDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Subject} and its DTO {@link SubjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {
    @Mapping(target = "majors", source = "majors", qualifiedByName = "majorIdSet")
    SubjectDTO toDto(Subject s);

    @Mapping(target = "majors", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);

    @Named("majorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MajorDTO toDtoMajorId(Major major);

    @Named("majorIdSet")
    default Set<MajorDTO> toDtoMajorIdSet(Set<Major> major) {
        return major.stream().map(this::toDtoMajorId).collect(Collectors.toSet());
    }
}
