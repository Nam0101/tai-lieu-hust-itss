package com.hust.itss.service.mapper;

import static com.hust.itss.domain.CommentsAsserts.*;
import static com.hust.itss.domain.CommentsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentsMapperTest {

    private CommentsMapper commentsMapper;

    @BeforeEach
    void setUp() {
        commentsMapper = new CommentsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCommentsSample1();
        var actual = commentsMapper.toEntity(commentsMapper.toDto(expected));
        assertCommentsAllPropertiesEquals(expected, actual);
    }
}
