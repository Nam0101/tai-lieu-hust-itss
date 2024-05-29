package com.hust.itss.service.mapper;

import static com.hust.itss.domain.MajorAsserts.*;
import static com.hust.itss.domain.MajorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MajorMapperTest {

    private MajorMapper majorMapper;

    @BeforeEach
    void setUp() {
        majorMapper = new MajorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMajorSample1();
        var actual = majorMapper.toEntity(majorMapper.toDto(expected));
        assertMajorAllPropertiesEquals(expected, actual);
    }
}
