package com.hust.itss.service.mapper;

import com.hust.itss.domain.Document;
import com.hust.itss.domain.Url;
import com.hust.itss.service.dto.DocumentDTO;
import com.hust.itss.service.dto.UrlDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Url} and its DTO {@link UrlDTO}.
 */
@Mapper(componentModel = "spring")
public interface UrlMapper extends EntityMapper<UrlDTO, Url> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    UrlDTO toDto(Url s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
