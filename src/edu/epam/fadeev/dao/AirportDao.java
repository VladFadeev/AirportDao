package edu.epam.fadeev.dao;

import edu.epam.fadeev.entity.Airline;
import edu.epam.fadeev.entity.Weekday;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;

public interface AirportDao {
    void add(Airline airline) throws DaoException;

    void delete(Airline airline) throws DaoException;

    void update(Airline airline) throws DaoException;

    Airline findByFlightNumber(int flightNumber)  throws DaoException;

    Airline findByDestinationAndDepartureAndWeekday(String destination, LocalTime departure, EnumSet<Weekday> weekdays)  throws DaoException;

    List<Airline> findByDestination(String destination) throws DaoException;

    List<Airline> findByWeekday(Weekday weekday) throws DaoException;

    List<Airline> findByWeekdayAndLaterTime(Weekday weekday, LocalTime time) throws DaoException;

    List<Airline> getAll() throws DaoException;

}
