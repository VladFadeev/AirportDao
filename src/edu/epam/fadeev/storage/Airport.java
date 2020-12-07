package edu.epam.fadeev.storage;

import edu.epam.fadeev.entity.Airline;

import java.util.List;

public class Airport {
    private List<Airline> schedule;

    private Airport(){
    }

    private static class AirportHolder {
        private final static Airport instance = new Airport();
    }

    public static Airport getInstance() {
        return AirportHolder.instance;
    }

    public void setSchedule(List<Airline> schedule) {
        this.schedule = schedule;
    }

    public List<Airline> getSchedule() {
        return schedule;
    }

    public int size() { return  schedule.size(); }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AirportSchedule{");
        sb.append("schedule=").append(schedule);
        sb.append('}');
        return sb.toString();
    }
}
