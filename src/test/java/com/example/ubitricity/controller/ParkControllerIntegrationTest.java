package com.example.ubitricity.controller;

import com.example.ubitricity.service.ParkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkController.class)
class ParkControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkService parkService;

    @Test
    void testPlugged() throws Exception {
        when(parkService.plug(1))
                .thenReturn("CP1 OCCUPIED 20A");

        mockMvc.perform(get("/park/plug/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("CP1 OCCUPIED 20A")));

        verify(parkService).plug(1);
    }

    @Test
    void testUnplugged() throws Exception {
        when(parkService.unplug(1))
                .thenReturn("CP1 AVAILABLE");

        mockMvc.perform(get("/park/unplug/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("CP1 AVAILABLE")));

        verify(parkService).unplug(1);
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
                "CP10 AVAILABLE";
        when(parkService.getReport())
                .thenReturn(report);

        mockMvc.perform(get("/park/report"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(report)));

        verify(parkService).getReport();
    }
}