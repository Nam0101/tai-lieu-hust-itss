package com.hust.itss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UrlTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Url getUrlSample1() {
        return new Url().id(1L).driveUrl("driveUrl1").type("type1");
    }

    public static Url getUrlSample2() {
        return new Url().id(2L).driveUrl("driveUrl2").type("type2");
    }

    public static Url getUrlRandomSampleGenerator() {
        return new Url().id(longCount.incrementAndGet()).driveUrl(UUID.randomUUID().toString()).type(UUID.randomUUID().toString());
    }
}
