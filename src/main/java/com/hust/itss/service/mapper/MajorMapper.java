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
 * Mapper for the entity {@link Major} and its DTO {@link MajorDTO}.
 */
@Mapper(componentModel = "spring")
public interface MajorMapper extends EntityMapper<MajorDTO, Major> {
    @Mapping(target = "subjects", source = "subjects", qualifiedByName = "subjectIdSet")
    MajorDTO toDto(Major s);

    @Mapping(target = "subjects", ignore = true)
    Major toEntity(MajorDTO majorDTO);

    @Named("subjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubjectDTO toDtoSubjectId(Subject subject);

    @Named("subjectIdSet")
    default Set<SubjectDTO> toDtoSubjectIdSet(Set<Subject> subject) {
        return subject.stream().map(this::toDtoSubjectId).collect(Collectors.toSet());
    }
}
