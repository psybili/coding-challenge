package com.example.ubitricity.controller;

import com.example.ubitricity.service.ParkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/park")
public class ParkController {

    private ParkService parkService;

    public ParkController(ParkService parkService) {
        this.parkService = parkService;
    }

    @GetMapping("/plug/{id}")
    public String plug(@PathVariable("id") int id) {
        return parkService.plug(id);
    }

    @GetMapping("/unplug/{id}")
    public String unplug(@PathVariable("id") int id) {
        return parkService.unplug(id);
    }

    @GetMapping("/report")
    public String getReport() {
        return parkService.getReport();
    }

}
