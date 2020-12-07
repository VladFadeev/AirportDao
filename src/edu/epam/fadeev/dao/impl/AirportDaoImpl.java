package edu.epam.fadeev.dao.impl;

import edu.epam.fadeev.dao.AirportDao;
import edu.epam.fadeev.dao.DaoException;
import edu.epam.fadeev.entity.Airline;
import edu.epam.fadeev.entity.Weekday;
import edu.epam.fadeev.service.AirportService;
import edu.epam.fadeev.storage.Airport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.*;


import static edu.epam.fadeev.dao.DaoErrorCode.*;

public class AirportDaoImpl implements AirportDao {
    private static final Logger log = LogManager.getLogger(AirportService.class);

    @Override
    public void add(Airline airline) throws DaoException {
        log.debug(">>Started add<<");
        if (airline != null) {
            List<Airline> schedule = Airport.getInstance().getSchedule();
            schedule.add(airline);
            Airport.getInstance().setSchedule(schedule);
        } else {
            log.error(EXC_DAO_002.toString());
            throw new DaoException(EXC_DAO_002);
        }
        log.debug(">>Finished add<<");
    }

    @Override
    public void delete(Airline airline) throws DaoException {
        log.debug(">>Started delete<<");
        List<Airline> schedule = Airport.getInstance().getSchedule();
        if (schedule.remove(airline)) {
            Airport.getInstance().setSchedule(schedule);
        } else {
            log.error(EXC_DAO_004.toString());
            throw new DaoException(EXC_DAO_004, airline);
        }
        log.debug(">>Finished delete<<");
    }

    @Override
    public void update(Airline airline) throws DaoException {
        log.debug(">>Started update<<");
        List<Airline> schedule = Airport.getInstance().getSchedule();
        try {
            Airline airlineUpdate = findByFlightNumber(airline.getFlightNumber());
            delete(airlineUpdate);
            add(airline);
            Airport.getInstance().setSchedule(schedule);
        } catch (DaoException e) {
            log.error(EXC_DAO_003.toString());
            throw new DaoException(e, EXC_DAO_003, airline);
        }
        log.debug(">>Finished update<<");
    }

    @Override
    public Airline findByFlightNumber(int flightNumber) throws DaoException {
        log.debug(">>Started findByFlightNumber<<");
        List<Airline> schedule = Airport.getInstance().getSchedule();
        Optional<Airline> airlineTemp = schedule.stream()
                .filter(airline -> airline.getFlightNumber() == flightNumber)
                .findFirst();
        if (airlineTemp.isPresent()) {
            log.debug(">>Finished findByFlightNumber<<");
            return new Airline(airlineTemp.get());
        } else {
            log.error(EXC_DAO_000.toString());
            throw new DaoException(EXC_DAO_000, flightNumber);
        }
    }

    @Override
    public Airline findByDestinationAndDepartureAndWeekday(String destination, LocalTime departure, EnumSet<Weekday> weekdays) throws DaoException {
        log.debug(">>findByDestinationAndDepartureAndWeekday<<");
        List<Airline> schedule = Airport.getInstance().getSchedule();
        Optional<Airline> airlineTemp = schedule.stream()
                .filter(airline -> (airline.getDeparture().compareTo(departure) >= 0 && airline.getWeekdays().equals(weekdays)
                        && airline.getDestination().equals(destination)))
                .findFirst();
        if (airlineTemp.isPresent()) {
            log.debug(">>Finished findByDestinationAndDepartureAndWeekday<<");
            return new Airline(airlineTemp.get());
        } else {
            log.error(EXC_DAO_000.toString());
            throw new DaoException(EXC_DAO_000, destination, departure, weekdays);
        }
    }

    @Override
    public List<Airline> findByDestination(String destination) throws DaoException {
        log.debug(">>Started findByDestination<<");
        if (Airport.getInstance().getSchedule().isEmpty()) {
            log.error(EXC_DAO_001.toString());
            throw new DaoException(EXC_DAO_001);
        }
        List<Airline> out = new ArrayList<>();
        List<Airline> schedule = Airport.getInstance().getSchedule();
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getDestination().equals(destination)) {
                out.add(new Airline(schedule.get(i)));
            }
        }
        log.debug(">>Finished findByDestination<<");
        return out;
    }

    @Override
    public List<Airline> findByWeekdayAndLaterTime(Weekday weekday, LocalTime time) throws DaoException {
        log.debug(">>Started findByWeekdayAndLaterTime<<");
        if (Airport.getInstance().getSchedule().isEmpty()) {
            log.error(EXC_DAO_001.toString());
            throw new DaoException(EXC_DAO_001);
        }
        List<Airline> schedule = Airport.getInstance().getSchedule();
        List<Airline> out = new ArrayList<>();
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isWeekdayIn(weekday) && time.compareTo(schedule.get(i).getDeparture()) < 0)
                out.add(new Airline(schedule.get(i)));
        }
        log.debug(">>Finished findByWeekdayAndLaterTime<<");
        return out;
    }

    @Override
    public List<Airline> findByWeekday(Weekday weekday) throws DaoException {
        log.debug(">>Started findByWeekday<<");
        if (Airport.getInstance().getSchedule().isEmpty()) {
            log.error(EXC_DAO_001.toString());
            throw new DaoException(EXC_DAO_001);
        }
        List<Airline> out = new ArrayList<>();
        List<Airline> schedule = Airport.getInstance().getSchedule();
        for (Airline airline : schedule) {
            if (airline.isWeekdayIn(weekday)) {
                out.add(airline);
            }
        }
        log.debug(">>Finished findByWeekday<<");
        return out;
    }

    @Override
    public List<Airline> getAll() throws DaoException {
        log.debug(">>Started getAll<<");
        if (!Airport.getInstance().getSchedule().isEmpty()) {
            List<Airline> airlineTemp = new ArrayList<>();
            for (int i = 0; i < Airport.getInstance().size(); i++) {
                Airline airline = new Airline(Airport.getInstance().getSchedule().get(i));
                airlineTemp.add(airline);
            }
            log.debug(">>Finished getAll<<");
            return airlineTemp;
        } else {
            log.error(EXC_DAO_001.toString());
            throw new DaoException(EXC_DAO_001, Airport.getInstance().getSchedule());
        }
    }
}
