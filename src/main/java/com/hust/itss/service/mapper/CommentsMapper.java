package com.hust.itss.service.mapper;

import com.hust.itss.domain.Comments;
import com.hust.itss.domain.Document;
import com.hust.itss.service.dto.CommentsDTO;
import com.hust.itss.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comments} and its DTO {@link CommentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentsMapper extends EntityMapper<CommentsDTO, Comments> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    CommentsDTO toDto(Comments s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
