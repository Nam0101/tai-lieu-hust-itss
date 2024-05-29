package com.hust.itss.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SubjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubjectAllPropertiesEquals(Subject expected, Subject actual) {
        assertSubjectAutoGeneratedPropertiesEquals(expected, actual);
        assertSubjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubjectAllUpdatablePropertiesEquals(Subject expected, Subject actual) {
        assertSubjectUpdatableFieldsEquals(expected, actual);
        assertSubjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubjectAutoGeneratedPropertiesEquals(Subject expected, Subject actual) {
        assertThat(expected)
            .as("Verify Subject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubjectUpdatableFieldsEquals(Subject expected, Subject actual) {
        assertThat(expected)
            .as("Verify Subject relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubjectUpdatableRelationshipsEquals(Subject expected, Subject actual) {
        assertThat(expected)
            .as("Verify Subject relationships")
            .satisfies(e -> assertThat(e.getMajor()).as("check major").isEqualTo(actual.getMajor()));
    }
}
