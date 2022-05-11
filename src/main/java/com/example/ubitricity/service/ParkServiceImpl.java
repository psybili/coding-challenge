package com.example.ubitricity.service;

import com.example.ubitricity.data.ParkDataSource;
import org.springframework.stereotype.Service;

@Service
public class ParkServiceImpl implements ParkService{

    private ParkDataSource parkDataSource;

    public ParkServiceImpl() {
        parkDataSource = ParkDataSource.getInstance();
    }

    @Override
    public String plug(int id) {
        return parkDataSource.plug(id);
    }

    @Override
    public String unplug(int id) {
        return parkDataSource.unplug(id);
    }

    @Override
    public String getReport() {
        return parkDataSource.getReport();
    }
}
