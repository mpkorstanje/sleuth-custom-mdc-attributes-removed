package com.example.demo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
public class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        WireMock.stubFor(
                WireMock.get(anyUrl())
                        .willReturn(okJson("{ \"content\": \"Hello world\"}")));

        mockMvc.perform(get("/greeting").header("user-id", "some-user-id"))
                .andDo(print())
                .andExpect(status().isOk());

        List<ServeEvent> allServeEvents = WireMock.getAllServeEvents();
        assertEquals(1, allServeEvents.size());
        String header = allServeEvents.get(0).getRequest().getHeader("user-id");
        assertEquals("some-user-id", header);
    }

}
