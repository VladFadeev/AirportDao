package edu.epam.fadeev.dao.impl;

import edu.epam.fadeev.AirportTestListener;
import edu.epam.fadeev.dao.AirportDao;
import edu.epam.fadeev.dao.DaoException;
import edu.epam.fadeev.entity.Airline;
import edu.epam.fadeev.entity.PlaneType;
import edu.epam.fadeev.entity.Weekday;
import edu.epam.fadeev.reader.Reader;
import edu.epam.fadeev.storage.Airport;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners(AirportTestListener.class)
public class AirportDaoImplTest {
    public AirportDao airportDao;
    public Airport airport;

    @BeforeClass
    public void init(){
        airport = Airport.getInstance();
        airport.setSchedule(Reader.read());
        airportDao = new AirportDaoImpl();
    }

    @Test
    public void testAdd() throws DaoException {
        Airline expected = new Airline("Minsk", 19, PlaneType.TU154, LocalTime.of(13, 30), new Weekday[]{Weekday.FRIDAY, Weekday.SATURDAY});
        airportDao.add(expected);
        Airline actual =  airport.getSchedule().get(11);
        assertEquals(actual, expected);
    }

    @Test
    public void testDelete() throws DaoException {
        List<Airline> expected = new ArrayList<>(airport.getSchedule());
        expected.remove(0);
        airportDao.delete(airport.getSchedule().get(0));
        List<Airline> actual = airport.getSchedule();
        assertEquals(actual, expected);
    }

    @Test
    public void testUpdate() throws DaoException {
        Airline expected = airport.getSchedule().get(0);
        expected.setDeparture(LocalTime.of(23, 0));
        airportDao.update(new Airline("New-York", 1257, PlaneType.values()[4], LocalTime.of(23, 0), new Weekday[]{Weekday.TUESDAY, Weekday.THURSDAY}));
        Airline actual = airport.getSchedule().get(10);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByFlightNumber() throws DaoException {
        Airline actual = airportDao.findByFlightNumber(6851);
        Airline expected = airport.getSchedule().get(7);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByDestinationAndDepartureAndWeekday() throws DaoException {
        Airline actual = airportDao.findByDestinationAndDepartureAndWeekday("Berlin", LocalTime.of(17,50), EnumSet.of(Weekday.THURSDAY, Weekday.FRIDAY));
        Airline expected = airportDao.findByFlightNumber(6851);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByDestination() throws DaoException {
        List<Airline> actual = airportDao.findByDestination("Berlin");
        List<Airline> expected = new ArrayList<>();
        expected.add(airportDao.findByFlightNumber(6851));
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByWeekdayAndLaterTime() throws DaoException {
        List<Airline> actual = airportDao.findByWeekdayAndLaterTime(Weekday.WEDNESDAY, LocalTime.of(16, 30));
        List<Airline> expected = new ArrayList<>();
        expected.add(airportDao.findByFlightNumber(4256));
        expected.add(airportDao.findByFlightNumber(1487));
        expected.add(airportDao.findByFlightNumber(5079));
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByWeekday() throws DaoException {
        List<Airline> actual = airportDao.findByWeekday(Weekday.MONDAY);
        List<Airline> expected = new ArrayList<>();
        expected.add(airportDao.findByFlightNumber(5030));
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Airline> actual = airportDao.getAll();
        List<Airline> expected = airport.getSchedule();
        assertEquals(actual, expected);
    }
}