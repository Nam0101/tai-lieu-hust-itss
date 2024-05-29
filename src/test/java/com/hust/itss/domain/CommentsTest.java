package com.hust.itss.domain;

import static com.hust.itss.domain.CommentsTestSamples.*;
import static com.hust.itss.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hust.itss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comments.class);
        Comments comments1 = getCommentsSample1();
        Comments comments2 = new Comments();
        assertThat(comments1).isNotEqualTo(comments2);

        comments2.setId(comments1.getId());
        assertThat(comments1).isEqualTo(comments2);

        comments2 = getCommentsSample2();
        assertThat(comments1).isNotEqualTo(comments2);
    }

    @Test
    void documentTest() throws Exception {
        Comments comments = getCommentsRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        comments.setDocument(documentBack);
        assertThat(comments.getDocument()).isEqualTo(documentBack);

        comments.document(null);
        assertThat(comments.getDocument()).isNull();
    }
}
