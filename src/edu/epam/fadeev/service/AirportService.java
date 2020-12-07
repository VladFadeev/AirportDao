package edu.epam.fadeev.service;

import edu.epam.fadeev.dao.AirportDao;
import edu.epam.fadeev.dao.DaoException;
import edu.epam.fadeev.dao.impl.AirportDaoImpl;
import edu.epam.fadeev.entity.Airline;
import edu.epam.fadeev.entity.Weekday;
import edu.epam.fadeev.reader.DataReader;
import edu.epam.fadeev.storage.Airport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.*;

public class AirportService {
    private final AirportDao airportDao;
    private static final Logger log = LogManager.getLogger(AirportService.class);

    private AirportService() {
        airportDao = new AirportDaoImpl();
        Airport.getInstance().setSchedule(DataReader.read());
    }

    private static class AirportServiceHolder {
        private final static AirportService instance = new AirportService();
    }

    public static AirportService getInstance() {
        return AirportService.AirportServiceHolder.instance;
    }

    public List<Airline> findAirlinesByDestination(String destination) throws DaoException {
        log.debug(">>Started findAirlinesByDestination<<");
        List<Airline> out = airportDao.findByDestination(destination);
        Arrays.sort(out.toArray());
        log.debug(">>Finished findAirlinesByDestination<<");
        return out;
    }

    public List<Airline> findAirlinesByWeekday(Weekday weekday) throws DaoException {
        log.debug(">>Started findAirlinesByWeekday<<");
        List<Airline> out = airportDao.findByWeekday(weekday);
        Collections.sort(out);
        log.debug(">>Finished findAirlinesByWeekday<<");
        return out;
    }

    public List<Airline> findAirlinesByWeekdayTime(Weekday weekday, LocalTime time) throws DaoException {
        log.debug(">>Start findAirlinesByWeekdayTime<<");
        List<Airline> out = airportDao.findByWeekdayAndLaterTime(weekday, time);
        Collections.sort(out);
        log.debug(">>Finished findAirlinesByWeekdayTime<<");
        return out;
    }
}
