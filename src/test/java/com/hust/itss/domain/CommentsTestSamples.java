package com.hust.itss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CommentsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Comments getCommentsSample1() {
        return new Comments().id(1L).anonymousId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Comments getCommentsSample2() {
        return new Comments().id(2L).anonymousId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Comments getCommentsRandomSampleGenerator() {
        return new Comments().id(longCount.incrementAndGet()).anonymousId(UUID.randomUUID());
    }
}
