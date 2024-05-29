package com.hust.itss.service.mapper;

import com.hust.itss.domain.Major;
import com.hust.itss.domain.Subject;
import com.hust.itss.service.dto.MajorDTO;
import com.hust.itss.service.dto.SubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subject} and its DTO {@link SubjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {
    @Mapping(target = "major", source = "major", qualifiedByName = "majorId")
    SubjectDTO toDto(Subject s);

    @Named("majorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MajorDTO toDtoMajorId(Major major);
}
