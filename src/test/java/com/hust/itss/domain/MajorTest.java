package com.hust.itss.domain;

import static com.hust.itss.domain.MajorTestSamples.*;
import static com.hust.itss.domain.SubjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hust.itss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MajorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Major.class);
        Major major1 = getMajorSample1();
        Major major2 = new Major();
        assertThat(major1).isNotEqualTo(major2);

        major2.setId(major1.getId());
        assertThat(major1).isEqualTo(major2);

        major2 = getMajorSample2();
        assertThat(major1).isNotEqualTo(major2);
    }

    @Test
    void subjectTest() throws Exception {
        Major major = getMajorRandomSampleGenerator();
        Subject subjectBack = getSubjectRandomSampleGenerator();

        major.setSubject(subjectBack);
        assertThat(major.getSubject()).isEqualTo(subjectBack);
        assertThat(subjectBack.getMajor()).isEqualTo(major);

        major.subject(null);
        assertThat(major.getSubject()).isNull();
        assertThat(subjectBack.getMajor()).isNull();
    }
}
