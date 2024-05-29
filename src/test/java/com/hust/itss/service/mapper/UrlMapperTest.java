package com.hust.itss.service.mapper;

import static com.hust.itss.domain.UrlAsserts.*;
import static com.hust.itss.domain.UrlTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlMapperTest {

    private UrlMapper urlMapper;

    @BeforeEach
    void setUp() {
        urlMapper = new UrlMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUrlSample1();
        var actual = urlMapper.toEntity(urlMapper.toDto(expected));
        assertUrlAllPropertiesEquals(expected, actual);
    }
}
