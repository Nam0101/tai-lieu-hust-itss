package com.hust.itss.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUrlAllPropertiesEquals(Url expected, Url actual) {
        assertUrlAutoGeneratedPropertiesEquals(expected, actual);
        assertUrlAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUrlAllUpdatablePropertiesEquals(Url expected, Url actual) {
        assertUrlUpdatableFieldsEquals(expected, actual);
        assertUrlUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUrlAutoGeneratedPropertiesEquals(Url expected, Url actual) {
        assertThat(expected)
            .as("Verify Url auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUrlUpdatableFieldsEquals(Url expected, Url actual) {
        assertThat(expected)
            .as("Verify Url relevant properties")
            .satisfies(e -> assertThat(e.getDriveUrl()).as("check driveUrl").isEqualTo(actual.getDriveUrl()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUrlUpdatableRelationshipsEquals(Url expected, Url actual) {
        assertThat(expected)
            .as("Verify Url relationships")
            .satisfies(e -> assertThat(e.getDocument()).as("check document").isEqualTo(actual.getDocument()));
    }
}
