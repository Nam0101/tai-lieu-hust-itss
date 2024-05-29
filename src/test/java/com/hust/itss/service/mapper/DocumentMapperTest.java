package com.hust.itss.service.mapper;

import static com.hust.itss.domain.DocumentAsserts.*;
import static com.hust.itss.domain.DocumentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentMapperTest {

    private DocumentMapper documentMapper;

    @BeforeEach
    void setUp() {
        documentMapper = new DocumentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentSample1();
        var actual = documentMapper.toEntity(documentMapper.toDto(expected));
        assertDocumentAllPropertiesEquals(expected, actual);
    }
}
