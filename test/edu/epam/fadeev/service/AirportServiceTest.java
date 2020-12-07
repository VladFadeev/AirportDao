package edu.epam.fadeev.service;

import edu.epam.fadeev.AirportTestListener;
import edu.epam.fadeev.dao.AirportDao;
import edu.epam.fadeev.dao.DaoException;
import edu.epam.fadeev.dao.impl.AirportDaoImpl;
import edu.epam.fadeev.entity.Airline;
import edu.epam.fadeev.entity.Weekday;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners(AirportTestListener.class)
public class AirportServiceTest {
    public AirportDao airportDao;
    public AirportService airportService;

    @BeforeClass
    public void init () {
        airportService = AirportService.getInstance();
        airportDao = new AirportDaoImpl();
    }

    @Test
    public void testFindAirlinesByDestination() throws DaoException {
        List<Airline> actual = airportService.findAirlinesByDestination("Moscow");
        List<Airline> expected = new ArrayList<>();
        expected.add(airportDao.findByFlightNumber(7476));
        expected.add(airportDao.findByFlightNumber(7480));
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAirlinesByWeekday() throws DaoException {
        List<Airline> actual = airportService.findAirlinesByWeekday(Weekday.MONDAY);
        List<Airline> expected = new ArrayList<>();
        expected.add(airportDao.findByFlightNumber(5030));
        expected.add(airportDao.findByFlightNumber(7476));
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAirlinesByWeekdayTime() throws DaoException {
        List<Airline> actual = airportService.findAirlinesByWeekdayTime(Weekday.WEDNESDAY, LocalTime.of(16, 30));
        List<Airline> expected = new ArrayList<>();
        expected.add(airportDao.findByFlightNumber(1487));
        expected.add(airportDao.findByFlightNumber(4256));
        expected.add(airportDao.findByFlightNumber(5079));
        assertEquals(actual, expected);
    }
}