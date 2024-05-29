package com.hust.itss.domain;

import static com.hust.itss.domain.DocumentTestSamples.*;
import static com.hust.itss.domain.MajorTestSamples.*;
import static com.hust.itss.domain.SubjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hust.itss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SubjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subject.class);
        Subject subject1 = getSubjectSample1();
        Subject subject2 = new Subject();
        assertThat(subject1).isNotEqualTo(subject2);

        subject2.setId(subject1.getId());
        assertThat(subject1).isEqualTo(subject2);

        subject2 = getSubjectSample2();
        assertThat(subject1).isNotEqualTo(subject2);
    }

    @Test
    void majorTest() throws Exception {
        Subject subject = getSubjectRandomSampleGenerator();
        Major majorBack = getMajorRandomSampleGenerator();

        subject.setMajor(majorBack);
        assertThat(subject.getMajor()).isEqualTo(majorBack);

        subject.major(null);
        assertThat(subject.getMajor()).isNull();
    }

    @Test
    void documentsTest() throws Exception {
        Subject subject = getSubjectRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        subject.addDocuments(documentBack);
        assertThat(subject.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getSubject()).isEqualTo(subject);

        subject.removeDocuments(documentBack);
        assertThat(subject.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getSubject()).isNull();

        subject.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(subject.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getSubject()).isEqualTo(subject);

        subject.setDocuments(new HashSet<>());
        assertThat(subject.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getSubject()).isNull();
    }
}
