package com.example.ubitricity.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkDataSource {

    private static ParkDataSource instance;

    public static int chargingPointsSize = 10;
    public static int availablePower = 100;

    private ChargingPoint[] chargingPoints;

    private List<ChargingPoint> chargingList;

    public ParkDataSource() {
        chargingPoints = new ChargingPoint[chargingPointsSize];
        for (int i = 0; i < chargingPoints.length; i++) {
            chargingPoints[i] = new ChargingPoint(i + 1);
        }
        chargingList = Collections.synchronizedList(new ArrayList<>());
    }

    public String plug(int id) {

        if (id < 1 || id > chargingPointsSize) {
            return "Undefined CP ID";
        }
        id--;

        ChargingPoint plugged = chargingPoints[id];

        if (plugged.getConsumption() == 0) {
            chargingList.add(plugged);
            plugged.setPriority(System.currentTimeMillis());
            if (availablePower >= 20) {
                plugged.setConsumption(20);
                availablePower -= 20;
            } else if (availablePower >= 10) {
                ChargingPoint oldest = getOldest();
                if (oldest != null) {
                    oldest.setConsumption(10);
                    availablePower -= 10;
                    plugged.setConsumption(20);
                }
            } else {
                ChargingPoint[] oldestTwo = getOldest2();
                oldestTwo[0].setConsumption(10);
                if (oldestTwo[1] != null) {
                    oldestTwo[1].setConsumption(10);
                    plugged.setConsumption(20);
                } else {
                    plugged.setConsumption(10);
                }
            }
        }
        return chargingPoints[id].toString();
    }

    private ChargingPoint[] getOldest2() {
        ChargingPoint[] oldestTwo = new ChargingPoint[2];
        for (int i = 0; i < chargingList.size() - 1; i++) {
            ChargingPoint candidate = chargingList.get(i);
            if (candidate.getPriority() != null && candidate.getConsumption() > 10) {
                if (oldestTwo[0] != null) {
                    if (candidate.getPriority() < oldestTwo[0].getPriority()) {
                        oldestTwo[0] = candidate;
                    } else {
                        if (oldestTwo[1] != null) {
                            if (candidate.getPriority() < oldestTwo[1].getPriority()) {
                                oldestTwo[1] = candidate;
                            }
                        } else {
                            oldestTwo[1] = candidate;
                        }
                    }
                } else {
                    oldestTwo[0] = candidate;
                }
            }
        }
        return oldestTwo;
    }

    private ChargingPoint getOldest() {
        ChargingPoint oldest = null;
        for (int i = 0; i < chargingList.size() - 1; i++) {
            ChargingPoint candidate = chargingList.get(i);
            if (candidate.getPriority() != null && candidate.getConsumption() > 10) {
                if (oldest != null) {
                    if (candidate.getPriority() < oldest.getPriority()) {
                        oldest = candidate;
                    }
                } else {
                    oldest = candidate;
                }
            }
        }
        return oldest;
    }

    public String unplug(int id) {

        if (id <= 0 || id > chargingPointsSize) {
            return "Undefined CP ID";
        }
        id -= 1;

        ChargingPoint plugged = chargingPoints[id];

        if (plugged.getConsumption() > 0) {
            int cpPower = plugged.getConsumption();
            plugged.setConsumption(0);
            plugged.setPriority(null);
            chargingList.remove(plugged);
            if (cpPower == 10) {
                ChargingPoint youngest = getYoungest();
                if (youngest != null) {
                    youngest.setConsumption(20);
                } else {
                    availablePower += 10;
                }
            } else if (cpPower == 20) {
                ChargingPoint[] youngestTwo = getYoungestTwoCP();
                if (youngestTwo[0] != null) {
                    youngestTwo[0].setConsumption(20);
                } else {
                    availablePower += 10;
                }
                if (youngestTwo[1] != null) {
                    youngestTwo[1].setConsumption(20);
                } else {
                    availablePower += 10;
                }
            }
        }

        return plugged.toString();
    }

    private ChargingPoint[] getYoungestTwoCP() {
        ChargingPoint[] youngestTwo = new ChargingPoint[2];
        for (int i = chargingList.size()-1; i >= 0 ; i--) {
            ChargingPoint candidate = chargingList.get(i);

            if (candidate.getPriority() != null && candidate.getConsumption() == 10) {
                if (youngestTwo[0] != null) {

                    if (youngestTwo[1] != null) {
                        break;
                    } else {
                        youngestTwo[1] = candidate;
                    }
                } else {
                    youngestTwo[0] = candidate;
                }
            }
        }
        return youngestTwo;
    }

    private ChargingPoint getYoungest() {
        ChargingPoint youngest = null;
        for (ChargingPoint candidate : chargingList) {
            if (candidate.getPriority() != null && candidate.getConsumption() == 10) {

                if (youngest == null) {
                    youngest = candidate;
                } else {
                    if (candidate.getPriority() > youngest.getPriority()) {
                        youngest = candidate;
                    }
                }

            }
        }
        return youngest;
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();

        for (ChargingPoint chargingPoint : chargingPoints) {
            report.append(chargingPoint.toString()).append("\n");
        }

        return report.toString();
    }

    public static ParkDataSource getInstance() {
        if (instance == null) {
            instance = new ParkDataSource();
        }
        return instance;
    }
}
