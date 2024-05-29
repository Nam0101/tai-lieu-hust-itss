package com.hust.itss.service.mapper;

import com.hust.itss.domain.Document;
import com.hust.itss.domain.Subject;
import com.hust.itss.service.dto.DocumentDTO;
import com.hust.itss.service.dto.SubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "subject", source = "subject", qualifiedByName = "subjectId")
    DocumentDTO toDto(Document s);

    @Named("subjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubjectDTO toDtoSubjectId(Subject subject);
}
