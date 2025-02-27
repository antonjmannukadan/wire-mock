package com.example.wiremock.controller;

import com.example.wiremock.config.BaseWireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest extends BaseWireMockTest {
    @Autowired
    private MockMvc mockMvc;



    @Test
    public void testGetQuoteWithWireMock() throws Exception {
        stubFor(get(urlEqualTo("/quotes/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":1,\"quote\":\"Imagination is more important than knowledge.\",\"author\":\"Albert Einstein\"}")));

        mockMvc.perform(MockMvcRequestBuilders.get("/quote"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"quote\":\"Imagination is more important than knowledge.\",\"author\":\"Albert Einstein\"}"));

        verify(getRequestedFor(urlEqualTo("/quotes/1")));
    }

    @Test
    public void testGetQuoteWithWireMockError() throws Exception {
        stubFor(get(urlEqualTo("/quotes/1"))
                .willReturn(aResponse()
                        .withStatus(500)));

        mockMvc.perform(MockMvcRequestBuilders.get("/quote"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(getRequestedFor(urlEqualTo("/quotes/1")));
    }
}
