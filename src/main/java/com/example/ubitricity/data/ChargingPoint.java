package com.example.ubitricity.data;

public class ChargingPoint {

    private int id;
    private int consumption;
    private Long priority;

    public ChargingPoint(int id) {
        this.id = id;
        consumption = 0;
        priority = null;
    }

    public ChargingPoint(int id, int consumption) {
        this.id = id;
        this.consumption = consumption;
        priority = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        if (consumption == 0) {
            return "CP" + id + " AVAILABLE";
        } else {
            return "CP" + id + " OCCUPIED " + consumption + "A";
        }
    }
}
