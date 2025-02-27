package com.example.wiremock.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class BaseWireMockTest {

    @BeforeAll
    static void setupWireMock() {
        WireMockServerManager.getWireMockServer();
    }

    @AfterAll
    static void teardownWireMock() {
        WireMockServerManager.releaseWireMockServer();
    }
}
