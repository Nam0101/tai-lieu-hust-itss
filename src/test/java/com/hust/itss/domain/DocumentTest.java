package com.hust.itss.domain;

import static com.hust.itss.domain.CommentsTestSamples.*;
import static com.hust.itss.domain.DocumentTestSamples.*;
import static com.hust.itss.domain.SubjectTestSamples.*;
import static com.hust.itss.domain.UrlTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hust.itss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = getDocumentSample1();
        Document document2 = new Document();
        assertThat(document1).isNotEqualTo(document2);

        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);

        document2 = getDocumentSample2();
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    void urlsTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Url urlBack = getUrlRandomSampleGenerator();

        document.addUrls(urlBack);
        assertThat(document.getUrls()).containsOnly(urlBack);
        assertThat(urlBack.getDocument()).isEqualTo(document);

        document.removeUrls(urlBack);
        assertThat(document.getUrls()).doesNotContain(urlBack);
        assertThat(urlBack.getDocument()).isNull();

        document.urls(new HashSet<>(Set.of(urlBack)));
        assertThat(document.getUrls()).containsOnly(urlBack);
        assertThat(urlBack.getDocument()).isEqualTo(document);

        document.setUrls(new HashSet<>());
        assertThat(document.getUrls()).doesNotContain(urlBack);
        assertThat(urlBack.getDocument()).isNull();
    }

    @Test
    void commentsTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Comments commentsBack = getCommentsRandomSampleGenerator();

        document.addComments(commentsBack);
        assertThat(document.getComments()).containsOnly(commentsBack);
        assertThat(commentsBack.getDocument()).isEqualTo(document);

        document.removeComments(commentsBack);
        assertThat(document.getComments()).doesNotContain(commentsBack);
        assertThat(commentsBack.getDocument()).isNull();

        document.comments(new HashSet<>(Set.of(commentsBack)));
        assertThat(document.getComments()).containsOnly(commentsBack);
        assertThat(commentsBack.getDocument()).isEqualTo(document);

        document.setComments(new HashSet<>());
        assertThat(document.getComments()).doesNotContain(commentsBack);
        assertThat(commentsBack.getDocument()).isNull();
    }

    @Test
    void subjectTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Subject subjectBack = getSubjectRandomSampleGenerator();

        document.setSubject(subjectBack);
        assertThat(document.getSubject()).isEqualTo(subjectBack);

        document.subject(null);
        assertThat(document.getSubject()).isNull();
    }
}
