package com.example.ubitricity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ParkControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPlugged() throws Exception {
        mockMvc.perform(get("/park/plug/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("CP1 OCCUPIED 20A")));
    }

    @Test
    void testUnplugged() throws Exception {
        mockMvc.perform(get("/park/unplug/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("CP1 AVAILABLE")));
    }

    @Test
    void getReport() throws Exception {
        String report = "CP1 AVAILABLE\n" +
                "CP2 AVAILABLE\n" +
                "CP3 AVAILABLE\n" +
                "CP4 AVAILABLE\n" +
                "CP5 AVAILABLE\n" +
                "CP6 AVAILABLE\n" +
                "CP7 AVAILABLE\n" +
                "CP8 AVAILABLE\n" +
                "CP9 AVAILABLE\n" +
                "CP10 AVAILABLE\n";

        mockMvc.perform(get("/park/unplug/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/park/report"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(report)));
    }
}