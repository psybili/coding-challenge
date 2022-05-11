package com.example.ubitricity.controller;

import com.example.ubitricity.service.ParkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ParkControllerUnitTest {

    private ParkService parkService;

    @BeforeEach
    void setUp() {
        parkService = Mockito.mock(ParkService.class);
    }

    @Test
    void plug() {
        when(parkService.plug(1)).thenReturn("CP1 OCCUPIED 20A");
        ParkController parkController = new ParkController(parkService);
        assertEquals("CP1 OCCUPIED 20A", parkController.plug(1));
    }

    @Test
    void unplug() {
        when(parkService.unplug(1)).thenReturn("CP1 AVAILABLE");
        ParkController parkController = new ParkController(parkService);
        assertEquals("CP1 AVAILABLE", parkController.unplug(1));
    }

    @Test
    void getReport() {
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
        when(parkService.getReport()).thenReturn(report);
        ParkController parkController = new ParkController(parkService);
        assertEquals(report, parkController.getReport());
    }
}