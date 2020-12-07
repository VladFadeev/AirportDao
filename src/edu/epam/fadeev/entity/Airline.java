package edu.epam.fadeev.entity;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumSet;

public class Airline implements Comparable<Airline> {
    private String destination;
    private int flightNumber;
    private PlaneType planeType;
    private LocalTime departure;
    private EnumSet<Weekday> weekdays;

    public Airline(String destination, int flightNumber, PlaneType planeType,
                   LocalTime departure, Weekday[] weekdays) {
        this.destination = destination;
        this.flightNumber = flightNumber;
        this.planeType = planeType;
        this.departure = departure;
        this.weekdays = EnumSet.copyOf(Arrays.asList(weekdays));
    }

    public Airline(String destination, int flightNumber, PlaneType planeType,
                   LocalTime departure, EnumSet<Weekday> weekdays) {
        this.destination = destination;
        this.flightNumber = flightNumber;
        this.planeType = planeType;
        this.departure = departure;
        this.weekdays = weekdays;
    }

    public Airline(Airline airline) {
        destination = airline.getDestination();
        flightNumber = airline.getFlightNumber();
        planeType = airline.getPlaneType();
        departure = airline.getDeparture();
        weekdays = EnumSet.copyOf(airline.getWeekdays());
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public  void setPlaneType(PlaneType planeType) {
        this.planeType = planeType;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    public  void setWeekdays(Weekday[] weekdays) {
        this.weekdays = EnumSet.copyOf(Arrays.asList(weekdays));
    }

    public String getDestination() {
        return destination;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public EnumSet<Weekday> getWeekdays() {
        return weekdays;
    }

    public boolean isWeekdayIn(Weekday weekday) {
        return weekdays.contains(weekday);
    }

    @Override
    public int compareTo(Airline airline) {
        return (this.flightNumber - airline.flightNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airline airline = (Airline) o;

        if (flightNumber != airline.flightNumber) return false;
        if (destination != null ? !destination.equals(airline.destination) : airline.destination != null) return false;
        if (planeType != airline.planeType) return false;
        if (departure != null ? !departure.equals(airline.departure) : airline.departure != null) return false;
        return weekdays != null ? weekdays.equals(airline.weekdays) : airline.weekdays == null;
    }

    @Override
    public int hashCode() {
        int result = destination != null ? destination.hashCode() : 0;
        result = 31 * result + flightNumber;
        result = 31 * result + (planeType != null ? planeType.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (weekdays != null ? weekdays.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Airline{");
        sb.append("destination='").append(destination).append('\'');
        sb.append(", flightNumber=").append(flightNumber);
        sb.append(", planeType=").append(planeType);
        sb.append(", departure=").append(departure);
        sb.append(", weekdays=").append(weekdays);
        sb.append('}');
        return sb.toString();
    }
}
