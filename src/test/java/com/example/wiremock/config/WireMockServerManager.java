package com.example.wiremock.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class WireMockServerManager {

    private static WireMockServer wireMockServer;
    private static int referenceCount = 0;

    public static synchronized WireMockServer getWireMockServer() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer();
            wireMockServer.start();
            configureFor("localhost", wireMockServer.port());
            System.setProperty("external.api.base.url", "http://localhost:" + wireMockServer.port()); // Set property here
        }
        referenceCount++;
        return wireMockServer;
    }

    public static synchronized void releaseWireMockServer() {
        referenceCount--;
        if (referenceCount == 0 && wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            wireMockServer = null;
            System.clearProperty("external.api.base.url"); // Clear property here
        }
    }
}
